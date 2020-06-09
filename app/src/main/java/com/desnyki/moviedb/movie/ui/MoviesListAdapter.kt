package com.desnyki.moviedb.movie.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.desnyki.moviedb.databinding.ListItemMovieBinding
import com.desnyki.moviedb.movie.data.MovieModel

class MovieListAdapter() : ListAdapter<MovieModel, MovieListAdapter.MyViewHolder>(DiffCallback()) {

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
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val movie = getItem(i)
        holder.apply {
            bind(createOnClickListener(movie), movie)
        }
    }

    private fun createOnClickListener(movie: MovieModel): View.OnClickListener {
        return View.OnClickListener {
            val action =
            MoviesFragmentDirections.actionMainFragmentToMovieDetailFragment(movie.id)
            it.findNavController().navigate(action)
        }

    }
}

private class DiffCallback : DiffUtil.ItemCallback<MovieModel>() {

    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}