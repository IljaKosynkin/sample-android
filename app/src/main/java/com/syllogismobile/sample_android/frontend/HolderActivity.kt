package com.syllogismobile.sample_android.frontend

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.syllogismobile.sample_android.R
import com.syllogismobile.sample_android.frontend.views.comic_view.ComicView

class HolderActivity: AppCompatActivity() {
    private lateinit var comicView: ComicView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comic_view_holder_activity)

        comicView = findViewById(R.id.root)
    }

    override fun onStart() {
        super.onStart()
        comicView.onStart()
    }

    override fun onPause() {
        super.onPause()
        comicView.onPause()
    }

    override fun onStop() {
        super.onStop()
        comicView.onStop()
    }

    override fun onResume() {
        super.onResume()
        comicView.onResume()
    }
}