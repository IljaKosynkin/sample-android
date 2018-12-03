package com.syllogismobile.sample_android.backend.repository

import com.syllogismobile.sample_android.backend.networking.XKCDService
import io.reactivex.Single

class OnlineRepository(private val service: XKCDService): Repository {
    override fun getCurrentComic(): Single<String> {
        return service.getCurrentComic()
            .map { it.body()?.string() }
    }
}