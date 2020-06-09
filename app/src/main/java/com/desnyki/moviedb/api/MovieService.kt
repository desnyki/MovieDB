package com.desnyki.moviedb.api

import com.desnyki.moviedb.model.MovieModel
import com.desnyki.moviedb.model.PageRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    companion object {
        const val ENDPOINT = "https://api.themoviedb.org/3/"
    }

    @GET("discover/movie")
    suspend fun retrieveMoviesAsync(
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1
    ): Response<PageRequest<MovieModel>>

    @GET("movie/{id}")
    suspend fun retrieveMovieAsync(
        @Path("id") id:Int
    ): Response<MovieModel>
}