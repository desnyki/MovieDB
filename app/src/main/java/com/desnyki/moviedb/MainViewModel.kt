package com.desnyki.moviedb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.desnyki.moviedb.api.AuthInterceptor
import com.desnyki.moviedb.api.MovieService
import com.desnyki.moviedb.data.AppDatabase
import com.desnyki.moviedb.model.MovieRemoteSource
import com.desnyki.moviedb.model.MovieRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val okHttpClient = OkHttpClient()

    private val repository: MovieRepository = MovieRepository(AppDatabase.getInstance(application).movieDao(), MovieRemoteSource(
        Retrofit.Builder()
            .baseUrl(MovieService.ENDPOINT)
            .client(okHttpClient
                .newBuilder()
                .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_BEARER_TOKEN))
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    ))

    val movies = repository.movies
    fun observeMovie(id: Int) = repository.movie(id)

}
