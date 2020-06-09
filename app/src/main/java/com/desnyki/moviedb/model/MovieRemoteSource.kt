package com.desnyki.moviedb.model

import com.desnyki.moviedb.api.BaseDataSource
import com.desnyki.moviedb.api.MovieService

class MovieRemoteSource constructor(private val service: MovieService) : BaseDataSource() {

    suspend fun fetchMovies() = getResult { service.retrieveMoviesAsync() }

    suspend fun fetchMovie(id: Int) = getResult { service.retrieveMovieAsync(id) }

}