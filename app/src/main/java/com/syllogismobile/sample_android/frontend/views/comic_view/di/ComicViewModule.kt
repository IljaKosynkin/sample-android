package com.syllogismobile.sample_android.frontend.views.comic_view.di

import com.syllogismobile.sample_android.backend.BackendMediator
import com.syllogismobile.sample_android.frontend.views.comic_view.ComicView
import com.syllogismobile.sample_android.frontend.views.comic_view.ComicViewModel
import com.syllogismobile.sample_android.frontend.views.comic_view.ComicViewPresenter
import dagger.Module
import dagger.Provides

@Module
class ComicViewModule(private val comicView: ComicView) {

    @ComicViewScope
    @Provides
    fun providesModel(backendMediator: BackendMediator): ComicViewModel {
        return ComicViewModel(backendMediator)
    }

    @ComicViewScope
    @Provides
    fun providesPresenter(comicViewModel: ComicViewModel): ComicViewPresenter {
        return ComicViewPresenter(comicView, comicViewModel)
    }
}