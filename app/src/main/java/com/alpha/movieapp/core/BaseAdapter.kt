package com.alpha.movieapp.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(private var items: ArrayList<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun setItem(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    var onItemClicked: OnItemClickedListener<T>? = null
    var onPositionItemClicked: OnPositionItemClickListener? = null

    interface OnItemClickedListener<T> {
        fun onItemClickListener(view: View, model: T)
    }

    interface OnPositionItemClickListener {
        fun onPositionItemClickListener(view: View, position: Int)
    }


    fun setOnItemClickListener(onItemClicked: OnItemClickedListener<T>) {
        this.onItemClicked = onItemClicked
    }

    fun setOnPositonItemClickListener(onPositionItemClicked: OnPositionItemClickListener) {
        this.onPositionItemClicked = onPositionItemClicked
    }


    fun getView(parent: ViewGroup, layoutItemRes: Int): View {
        val inflater = LayoutInflater.from(parent.context)
        return inflater.inflate(layoutItemRes, parent, false)
    }

    fun delete(model: T) {
        items.remove(model)
        notifyDataSetChanged()
    }

    fun deleteAll() {
        items.clear()
        notifyDataSetChanged()
    }


    fun updateItems(data: ArrayList<T>) {
        this.items.clear()
        this.items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return setItem(parent, viewType)
    }

    override fun getItemCount(): Int = items.size


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}