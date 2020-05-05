package com.alpha.movieapp.utilities

import android.view.View
import android.widget.ImageView
import com.alpha.movieapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun View.load(url: String, placeHolder: Int = R.drawable.placeholder, radius: Int = 1) {
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transform(RoundedCorners(radius))
    Glide.with(this.context).load(url).placeholder(placeHolder).apply(requestOptions)
        .into(this as ImageView)
}

fun ArrayList<String>.concat(): String {
    return joinToString { " " }
}



