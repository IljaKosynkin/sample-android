package com.syllogismobile.sample_android.frontend.views.comic_view

import com.syllogismobile.sample_android.backend.BackendMediator
import com.syllogismobile.sample_android.backend.model.Comic
import io.reactivex.Single

class ComicViewModel(private val backendMediator: BackendMediator) {
    fun getCurrentComic(): Single<Comic> {
        return backendMediator.getCurrentComic()
    }
}