package com.syllogismobile.sample_android.frontend.views.comic_view

import com.syllogismobile.sample_android.backend.model.Comic
import com.syllogismobile.sample_android.frontend.views.base.Presenter
import io.reactivex.Single

class ComicViewPresenter(
    private val comicView: ComicView,
    private val comicModel: ComicViewModel
): Presenter {
    fun getCurrentComic(): Single<Comic> {
        return comicModel.getCurrentComic()
    }
}