package com.syllogismobile.sample_android

import android.app.Application
import com.syllogismobile.sample_android.backend.di.BackendComponent
import com.syllogismobile.sample_android.backend.di.BackendModule
import com.syllogismobile.sample_android.backend.di.DaggerBackendComponent
import io.realm.Realm

class SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        component = DaggerBackendComponent
            .builder()
            .backendModule(BackendModule())
            .build()
    }

    companion object {
        lateinit var component: BackendComponent
    }
}