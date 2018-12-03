package com.syllogismobile.sample_android.backend.di

import com.squareup.moshi.Moshi
import com.syllogismobile.sample_android.backend.BackendMediator
import com.syllogismobile.sample_android.backend.converters.StringToComicConverter
import com.syllogismobile.sample_android.backend.converters.factory.ConverterFactory
import com.syllogismobile.sample_android.backend.converters.factory.DefaultConverterFactory
import com.syllogismobile.sample_android.backend.database.Database
import com.syllogismobile.sample_android.backend.database.RealmDatabase
import com.syllogismobile.sample_android.backend.logger.DefaultLogger
import com.syllogismobile.sample_android.backend.logger.Logger
import com.syllogismobile.sample_android.backend.model.Comic
import com.syllogismobile.sample_android.backend.networking.XKCDService
import com.syllogismobile.sample_android.backend.repository.OfflineRepository
import com.syllogismobile.sample_android.backend.repository.OnlineRepository
import com.syllogismobile.sample_android.backend.repository.Repository
import dagger.Module
import dagger.Provides
import io.realm.RealmModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class BackendModule {
    @Provides
    @Singleton
    fun providesXKCDService(): XKCDService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://xkcd.com")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(XKCDService::class.java)
    }

    @Named("online")
    @Provides
    @Singleton
    fun providesOnlineRepository(service: XKCDService): Repository {
        return OnlineRepository(service)
    }

    @Provides
    @Singleton
    fun providesLogger(): Logger {
        return DefaultLogger()
    }

    @Provides
    @Singleton
    fun providesRealmDatabase(logger: Logger): Database<RealmModel> {
        return RealmDatabase(logger)
    }

    @Named("offline")
    @Provides
    @Singleton
    fun providesOfflineRepository(database: Database<RealmModel>): Repository {
        return OfflineRepository(database)
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): ConverterFactory {
        val moshi = Moshi.Builder().build()

        return DefaultConverterFactory()
            .register(Pair(String::class.java, Comic::class.java), StringToComicConverter(moshi))
    }

    @Provides
    @Singleton
    fun providesBackendMediator(
        @Named("offline") offlineRepository: Repository,
        @Named("online") onlineRepository: Repository,
        realmDatabase: Database<RealmModel>,
        converterFactory: ConverterFactory,
        logger: Logger
    ): BackendMediator {

        return BackendMediator(
            offlineRepository,
            onlineRepository,
            realmDatabase,
            converterFactory,
            logger
        )
    }
}