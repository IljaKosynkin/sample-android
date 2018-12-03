package com.syllogismobile.sample_android.backend.repository

import io.reactivex.Single

interface Repository {
    fun getCurrentComic(): Single<String>
}