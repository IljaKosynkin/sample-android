package com.syllogismobile.sample_android.backend.model

import io.realm.RealmModel
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import io.realm.annotations.PrimaryKey

@RealmClass
open class CachedResponse: RealmModel {
    @Required
    @PrimaryKey
    var id: String = DEFAULT_ID
    var content: String = ""

    companion object {
        const val DEFAULT_ID = "default"

        fun with(content: String): CachedResponse {
            val response = CachedResponse()
            response.content = content
            return response
        }
    }
}