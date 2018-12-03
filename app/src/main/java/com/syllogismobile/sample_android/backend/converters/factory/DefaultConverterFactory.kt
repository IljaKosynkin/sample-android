package com.syllogismobile.sample_android.backend.converters.factory

import com.syllogismobile.sample_android.backend.converters.Converter


class DefaultConverterFactory: ConverterFactory {
    private val converters: MutableMap<String, Converter<Any, Any>> = mutableMapOf()

    fun <In, Out> register(id: Pair<Class<In>, Class<Out>>, converter: Converter<In, Out>): DefaultConverterFactory {
        converters[idToString(id)] = converter as Converter<Any, Any>
        return this
    }

    override fun provide(id: Pair<Class<*>, Class<*>>): Converter<Any, Any>? {
        return converters[idToString(id)]
    }

    private fun idToString(id: Pair<Class<*>, Class<*>>): String {
        return "${id.first}$SEPARATOR${id.second}"
    }

    companion object {
        private const val SEPARATOR = "\\o//"
    }
}