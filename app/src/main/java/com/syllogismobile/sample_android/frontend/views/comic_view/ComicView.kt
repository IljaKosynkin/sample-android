package com.syllogismobile.sample_android.frontend.views.comic_view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.support.design.button.MaterialButton
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewAnimator
import com.squareup.picasso.Picasso
import com.syllogismobile.sample_android.R
import com.syllogismobile.sample_android.backend.model.Comic
import com.syllogismobile.sample_android.frontend.views.base.EmbeddedView
import com.syllogismobile.sample_android.frontend.views.comic_view.di.ComicViewGraph
import com.syllogismobile.sample_android.frontend.views.comic_view.di.ComicViewModule
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject
import android.view.View as AndroidView

class ComicView: LinearLayout, EmbeddedView {
    private val appBarTitle: TextView
    private val separator: AndroidView
    private val animator: ViewAnimator

    private val placeholderImage: ImageView
    private val placeholderTitle: TextView
    private val placeholderReloadButton: MaterialButton

    private val comicImage: ImageView
    private val comicDescription: TextView

    private var subscription: Disposable? = null
    private val graph: ComicViewGraph = ComicViewGraph(this)

    @Inject
    lateinit var presenter: ComicViewPresenter

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        graph.inject(this)

        LayoutInflater
            .from(context)
            .inflate(R.layout.comic_view, this, true)

        appBarTitle = findViewById(R.id.toolbar_title)
        separator = findViewById(R.id.separator)
        animator = findViewById(R.id.animator)

        placeholderImage = findViewById(R.id.placeholder_gears)
        placeholderTitle = findViewById(R.id.placeholder_title)
        placeholderReloadButton = findViewById(R.id.placeholder_reload_button)

        comicImage = findViewById(R.id.comic_image)
        comicDescription = findViewById(R.id.comic_description)

        val nepalColor = resources.getColor(R.color.nepal)
        val outerSpace = resources.getColor(R.color.outer_space)

        var backgroundColor = outerSpace

        var toolbarColor = nepalColor
        var separatorColor = nepalColor

        var placeholderImageTintColor = nepalColor
        var placeholderTitleColor = nepalColor
        var placeholderButtonBackground = outerSpace
        var placeholderButtonTitleColor = nepalColor

        var comicDescriptionColor = nepalColor

        attrs?.let {
            val attrs = R.styleable.ComicView.toMutableList()
            attrs.add(android.R.attr.background)

            val typedArray = context.obtainStyledAttributes(it, attrs.toIntArray())

            backgroundColor = typedArray.getColor(attrs.size - 1, outerSpace)

            toolbarColor = typedArray.getColor(R.styleable.ComicView_toolbar_color, nepalColor)
            separatorColor = typedArray.getColor(R.styleable.ComicView_separator_color, nepalColor)

            placeholderImageTintColor = typedArray.getColor(R.styleable.ComicView_placeholder_image_tint, nepalColor)
            placeholderTitleColor = typedArray.getColor(R.styleable.ComicView_placeholder_image_tint, nepalColor)
            placeholderButtonBackground = typedArray.getColor(R.styleable.ComicView_placeholder_reload_button_background_color, outerSpace)
            placeholderButtonTitleColor = typedArray.getColor(R.styleable.ComicView_placeholder_reload_button_title_color, nepalColor)

            comicDescriptionColor = typedArray.getColor(R.styleable.ComicView_comic_description_color, nepalColor)

            typedArray.recycle()
        }

        orientation = VERTICAL

        setBackgroundColor(backgroundColor)

        appBarTitle.setTextColor(toolbarColor)
        separator.setBackgroundColor(separatorColor)

        placeholderImage.setColorFilter(placeholderImageTintColor)
        placeholderTitle.setTextColor(placeholderTitleColor)
        placeholderReloadButton.strokeColor = ColorStateList.valueOf(placeholderButtonTitleColor)
        placeholderReloadButton.setTextColor(placeholderButtonTitleColor)
        placeholderReloadButton.setBackgroundColor(placeholderButtonBackground)

        comicDescription.setTextColor(comicDescriptionColor)

        placeholderReloadButton.setOnClickListener { reload() }
    }

    private fun reload() {
        showLoading()

        subscription = presenter.getCurrentComic()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::showComic, this::showError)
    }

    private fun showLoading() {
        appBarTitle.visibility = AndroidView.GONE
        separator.visibility = AndroidView.GONE
        placeholderTitle.visibility = AndroidView.GONE
        placeholderReloadButton.visibility = AndroidView.GONE

        placeholderImage.visibility = AndroidView.VISIBLE
        //(placeholderImage.drawable as? AnimatedVectorDrawable)?.start()

        animator.displayedChild = LOADING_PLACEHOLDER_CHILD
    }

    private fun showComic(comic: Comic) {

        appBarTitle.visibility = AndroidView.VISIBLE
        separator.visibility = AndroidView.VISIBLE

        Picasso
            .get()
            .load(comic.image)
            .into(comicImage)

        comicDescription.text = comic.description

        animator.displayedChild = CONTENT_CHILD
    }

    private fun showError(ignored: Throwable) {
        appBarTitle.visibility = AndroidView.VISIBLE
        separator.visibility = AndroidView.VISIBLE
        placeholderTitle.visibility = AndroidView.VISIBLE
        placeholderReloadButton.visibility = AndroidView.VISIBLE
        placeholderImage.visibility = AndroidView.VISIBLE

        animator.displayedChild = LOADING_PLACEHOLDER_CHILD
    }

    fun setModule(comicViewModule: ComicViewModule) {
        graph.setModule(comicViewModule)
        graph.inject(this)
    }

    override fun onStart() {

    }

    override fun onPause() {
        subscription?.dispose()
    }

    override fun onResume() {
        reload()
    }

    override fun onStop() {

    }

    companion object {
        private const val LOADING_PLACEHOLDER_CHILD = 0
        private const val CONTENT_CHILD = 1
    }
}