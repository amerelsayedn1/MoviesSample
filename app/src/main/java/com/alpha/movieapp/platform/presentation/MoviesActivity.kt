package com.alpha.movieapp.platform.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alpha.movieapp.R
import com.alpha.movieapp.platform.network.MoviesRepo
import com.alpha.movieapp.core.data.Movie
import com.alpha.movieapp.platform.presentation.adapter.EndlessRecyclerViewScrollListener
import com.alpha.movieapp.platform.presentation.adapter.MoviesAdapter
import com.alpha.movieapp.utilities.concat
import kotlinx.android.synthetic.main.activity_main.*


class MoviesActivity : AppCompatActivity(R.layout.activity_main) {

    private var pageIndex = 1
    private lateinit var moviesAdapter: MoviesAdapter
    private var movieList = ArrayList<Movie>()


    private val viewModel by lazy {
        ViewModelProvider(this, MoviesViewModelFactory(MoviesRepo())).get(MoviesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()

        viewModel.getPopularMovies()

        btnSearch.setOnClickListener {
            movieList.clear()
            viewModel.getMovies(etSearch.text.toString())
        }

        viewModel.movieSuccessResponse.observe(this, Observer {
            if (it.totalPages >= pageIndex && it.data.isNotEmpty()) {
                movieList.addAll(it.data)
                moviesAdapter.updateItems(movieList)
            } else {
                moviesAdapter.deleteAll()
                showAlert(getString(R.string.error), getString(R.string.noItems))
            }
        })

        viewModel.movieErrorResponse.observe(this, Observer {

            if (it.errors.isNotEmpty()) {
                showAlert(getString(R.string.error), it.errors.concat())
            } else {
                showAlert(getString(R.string.error), it.message)
            }

        })

    }

    private fun initAdapter() {
        val manager = LinearLayoutManager(this@MoviesActivity, RecyclerView.VERTICAL, false)
        rvMovies.layoutManager = manager
        moviesAdapter =
                MoviesAdapter(arrayListOf())
        rvMovies.adapter = moviesAdapter

        rvMovies.addOnScrollListener(object : EndlessRecyclerViewScrollListener(manager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                Log.e("RRRR", "$pageIndex")
                ++pageIndex
                viewModel.getMovies(etSearch.text.toString(), page)
            }
        })
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this@MoviesActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes) { dialog, _ ->
                    dialog.dismiss()
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }

}
