package com.alpha.movieapp.platform.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alpha.movieapp.R
import com.alpha.movieapp.core.BaseAdapter
import com.alpha.movieapp.core.BaseViewHolder
import com.alpha.movieapp.core.data.Movie
import com.alpha.movieapp.utilities.load
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(private val data: ArrayList<Movie>) : BaseAdapter<Movie>(data) {

    override fun setItem(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainSectionsViewHolder(getView(parent, R.layout.item_movie))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as MainSectionsViewHolder
        itemHolder.bind(data[position])
    }

    inner class MainSectionsViewHolder(item: View) : BaseViewHolder<Movie>(item) {

        private val ivMovieImg = item.ivMovieImg
        private val tvMovieTitle = item.tvMovieTitle
        private val tvMovieReleaseDate = item.tvMovieReleaseDate
        private val tvMovieDescription = item.tvMovieDescription

        override fun bind(data: Movie) {
            data.apply {
                ivMovieImg.load("http://image.tmdb.org/t/p/w780$posterPath")
                tvMovieTitle.text = title
                tvMovieReleaseDate.text = releaseDate
                tvMovieDescription.text = overview
            }
        }
    }

}