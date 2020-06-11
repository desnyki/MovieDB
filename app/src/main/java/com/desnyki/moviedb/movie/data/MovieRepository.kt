package com.desnyki.moviedb.movie.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import com.desnyki.moviedb.data.Result
import com.desnyki.moviedb.data.resultLiveData
import com.desnyki.moviedb.movie.data.MovieDao
import com.desnyki.moviedb.movie.data.MovieRemoteSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val dao: MovieDao,
                                          private val remoteSource: MovieRemoteSource
) {

    val movies =
        resultLiveData(
            databaseQuery = { dao.getMovies() },
            networkCall = { remoteSource.fetchMovies() },
            saveCallResult = { dao.insertAll(it.results) }
        )

    fun movie(id: Int): LiveData<Result<MovieModel>> = resultLiveData(
        databaseQuery = { dao.getMovie(id) },
        networkCall = { remoteSource.fetchMovie(id) },
        saveCallResult = { dao.insert(it) }
        ).distinctUntilChanged()

}