package com.syllogismobile.sample_android.backend.database

import io.reactivex.Single

interface Database<Base> {
    fun <T: Base> get(id: String, clazz: Class<T>): Single<T>
    fun <T: Base> save(obj: T)
}