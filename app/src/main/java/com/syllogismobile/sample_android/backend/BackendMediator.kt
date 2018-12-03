package com.syllogismobile.sample_android.backend

import com.syllogismobile.sample_android.backend.converters.Converter
import com.syllogismobile.sample_android.backend.converters.factory.ConverterFactory
import com.syllogismobile.sample_android.backend.database.Database
import com.syllogismobile.sample_android.backend.logger.Logger
import com.syllogismobile.sample_android.backend.model.CachedResponse
import com.syllogismobile.sample_android.backend.model.Comic
import com.syllogismobile.sample_android.backend.repository.Repository
import io.reactivex.Single
import io.realm.RealmModel

class BackendMediator(
    private val offlineRepository: Repository,
    private val onlineRepository: Repository,
    private val database: Database<RealmModel>,
    private val converterFactory: ConverterFactory,
    private val logger: Logger
) {

    fun getCurrentComic(): Single<Comic> {
        return onlineRepository.getCurrentComic()
            .doOnSuccess { database.save(CachedResponse.with(it)) }
            .onErrorResumeNext {
                it.printStackTrace()
                offlineRepository.getCurrentComic()
            }
            .flatMap { convert<String, Comic>(it)}

    }

    private inline fun <reified In, reified Out> convert(input: In): Single<Out> {
        val converter = converterFactory.provide(Pair(In::class.java, Out::class.java)) as Converter<In, Out>?
        if (converter == null) {
            logger.log(Logger.Level.ERROR, "Converter not found for ${In::class.java} to ${Out::class.java}")
            return Single.error(Throwable("Converter not found for ${In::class.java} to ${Out::class.java}"))
        }

        val out = converter.convert(input)
        if (out == null) {
            logger.log(Logger.Level.ERROR, "Could not convert $input to ${Out::class.java}")
            return Single.error(Throwable("Could not convert $input to ${Out::class.java}"))
        }

        return Single.just(out)
    }
}