package com.syllogismobile.sample_android.backend.converters

interface Converter<In, Out> {
    fun convert(input: In): Out?
}