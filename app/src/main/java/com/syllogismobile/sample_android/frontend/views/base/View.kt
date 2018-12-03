package com.syllogismobile.sample_android.frontend.views.base

interface View

interface EmbeddedView: View {
    fun onStart()
    fun onPause()
    fun onResume()
    fun onStop()
}
