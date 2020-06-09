package com.desnyki.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.desnyki.moviedb.databinding.ListItemMovieBinding
import com.desnyki.moviedb.model.MovieModel

typealias AdapterCallbacks = (movieId: Int) -> Unit

class MovieListAdapter(val callback: AdapterCallbacks) :
    RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    var moviesList: List<MovieModel> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(
        private val binding: ListItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: MovieModel) {
            binding.apply {
                clickListener = listener
                movie = item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = moviesList.size


    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {

        holder.apply {
            bind(createOnClickListener(moviesList[i]), moviesList[i])
        }
    }

    private fun createOnClickListener(movie: MovieModel): View.OnClickListener {
        return View.OnClickListener {
            callback.invoke(movie.id)
        }
    }
}