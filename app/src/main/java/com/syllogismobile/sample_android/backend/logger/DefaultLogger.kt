package com.syllogismobile.sample_android.backend.logger

import android.util.Log

class DefaultLogger: Logger {
    override fun log(level: Logger.Level, message: String, vararg args: Any) {
        when (level) {
            Logger.Level.DEBUG -> Log.d(TAG, String.format(message, args))
            Logger.Level.VERBOSE -> Log.v(TAG, String.format(message, args))
            Logger.Level.ERROR -> Log.e(TAG, String.format(message, args))
        }
    }

    companion object {
        private const val TAG = "Logger"
    }
}