package com.neugelb.binding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.neugelb.config.THRESHOLD_CLICK_TIME


@SuppressLint("CheckResult")
@BindingAdapter(
    value = [
        "imageUrl",
        "thumbnailUrl",
        "placeholder",
        "errorDrawable",
        "requestListener",
        "centerCrop",
        "fitCenter",
        "circleCrop",
        "skipMemoryCache",
        "diskCacheStrategy",
        "signatureKey"
    ],
    requireAll = false
)
fun ImageView.loadImage(
    imageUrl: String? = null,
    thumbnailUrl: String? = null,
    placeHolderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    requestListener: RequestListener<Drawable>? = null,
    centerCrop: Boolean = false,
    fitCenter: Boolean = false,
    circleCrop: Boolean = false,
    skipMemoryCache: Boolean? = null,
    diskCacheStrategy: DiskCacheStrategy? = null,
    signatureKey: String? = null
) {
    if (imageUrl.isNullOrBlank()) {
        if (placeHolderDrawable != null) setImageDrawable(placeHolderDrawable)
        return
    }

    val requestOptions = RequestOptions().apply {
        if (skipMemoryCache != null) skipMemoryCache(skipMemoryCache)
        diskCacheStrategy(diskCacheStrategy ?: DiskCacheStrategy.RESOURCE)

        if (placeHolderDrawable != null) placeholder(placeHolderDrawable)
        if (errorDrawable != null) error(errorDrawable)

        // crop type
        if (centerCrop) centerCrop()
        if (fitCenter) fitCenter()
        if (circleCrop) circleCrop()

        if (!signatureKey.isNullOrBlank()) {
            signature(ObjectKey(signatureKey))
        }
    }

    Glide.with(context).load(imageUrl).apply {
        transition(DrawableTransitionOptions.withCrossFade())
        addListener(requestListener)
        apply(requestOptions)
    }.into(this)
}

@BindingAdapter("onSingleClick")
fun View.setSingleClick(execution: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var lastClickTime: Long = 0

        override fun onClick(p0: View?) {
            if (System.currentTimeMillis() - lastClickTime < THRESHOLD_CLICK_TIME) return
            lastClickTime = System.currentTimeMillis()
            execution.invoke()
        }
    })
}

@BindingAdapter("onBottomCheck")
fun RecyclerView.setBottomListener(execution: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                execution.invoke()
            }
        }
    })
}



