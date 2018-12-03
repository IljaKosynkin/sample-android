package com.syllogismobile.sample_android.backend.di

import com.syllogismobile.sample_android.backend.BackendMediator
import com.syllogismobile.sample_android.backend.logger.Logger
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BackendModule::class])
interface BackendComponent {
    fun providesMediator(): BackendMediator
    fun providesLogger(): Logger
}