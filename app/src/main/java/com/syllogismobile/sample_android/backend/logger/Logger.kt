package com.syllogismobile.sample_android.backend.logger

interface Logger {
    enum class Level {
        VERBOSE,
        DEBUG,
        ERROR
    }

    fun log(level: Level, message: String, vararg args: Any)
}