package com.alpha.movieapp.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(item: View) : RecyclerView.ViewHolder(item) {
    abstract fun bind(data: T)
}