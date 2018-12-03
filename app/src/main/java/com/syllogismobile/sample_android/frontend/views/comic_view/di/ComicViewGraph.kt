package com.syllogismobile.sample_android.frontend.views.comic_view.di

import com.syllogismobile.sample_android.SampleApplication
import com.syllogismobile.sample_android.backend.di.BackendComponent
import com.syllogismobile.sample_android.frontend.views.comic_view.ComicView
import dagger.Component

class ComicViewGraph(private val comicView: ComicView) {
    private val builder: DaggerComicViewGraph_ComicViewComponent.Builder = DaggerComicViewGraph_ComicViewComponent
                                                                            .builder()
                                                                            .backendComponent(SampleApplication.component)
                                                                            .comicViewModule(ComicViewModule(comicView))

    fun setModule(module: ComicViewModule) {
        builder.comicViewModule(module)
    }

    fun inject(comicView: ComicView) {
        builder.build().inject(comicView)
    }

    @ComicViewScope
    @Component(dependencies = [BackendComponent::class], modules = [ComicViewModule::class])
    interface ComicViewComponent {
        fun inject(comicView: ComicView)
    }
}