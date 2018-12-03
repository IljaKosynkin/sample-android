package com.syllogismobile.sample_android.backend.factory

interface Factory<IDType, Stored> {
    fun provide(id: IDType): Stored?
}

