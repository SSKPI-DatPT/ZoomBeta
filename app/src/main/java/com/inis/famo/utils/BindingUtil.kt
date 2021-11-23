package com.inis.famo.utils

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.inis.famo.BuildConfig
import java.io.File

fun ImageView.load(
    pictureUrl: String,
    requestOptions: RequestOptions,
    callbackFail: (() -> Unit)? = null
) {
    try {
        Glide.with(context)
            .load(AssetHelper.encodeUrl(pictureUrl))
            .apply(requestOptions)
            .listener(
                object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        callbackFail?.invoke()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }
            )
            .into(this)
    } catch (e: Exception) {
        callbackFail?.invoke()
    }
}

@BindingAdapter("isSelected")
fun setSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}

@BindingAdapter("loadImageDrawable")
fun ImageView.loadImageDrawable(id: Int) {
    setImageResource(id)
}