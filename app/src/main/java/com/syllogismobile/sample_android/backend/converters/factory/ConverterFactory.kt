package com.syllogismobile.sample_android.backend.converters.factory

import com.syllogismobile.sample_android.backend.converters.Converter
import com.syllogismobile.sample_android.backend.factory.Factory

interface ConverterFactory: Factory<Pair<Class<*>, Class<*>>, Converter<Any, Any>>