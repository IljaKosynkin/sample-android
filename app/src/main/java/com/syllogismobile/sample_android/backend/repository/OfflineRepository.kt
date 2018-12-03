package com.syllogismobile.sample_android.backend.repository

import com.syllogismobile.sample_android.backend.database.Database
import com.syllogismobile.sample_android.backend.model.CachedResponse
import io.reactivex.Single
import io.realm.RealmModel

class OfflineRepository(private val database: Database<RealmModel>): Repository {
    override fun getCurrentComic(): Single<String> {
        return database.get(CachedResponse.DEFAULT_ID, CachedResponse::class.java)
            .map { it.content }
    }
}