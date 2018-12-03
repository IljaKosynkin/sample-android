package com.syllogismobile.sample_android.backend.networking

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface XKCDService {
    @GET("/info.0.json")
    fun getCurrentComic(): Single<Response<ResponseBody>>
}