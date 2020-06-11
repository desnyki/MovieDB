package com.desnyki.moviedb.movie.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.desnyki.moviedb.data.Result
import com.desnyki.moviedb.movie.data.MovieModel
import com.desnyki.moviedb.movie.data.MovieRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {

    val movies by lazy { repository.movies }

    fun observeMovie(id: Int): LiveData<Result<MovieModel>> = repository.movie(id)

}
