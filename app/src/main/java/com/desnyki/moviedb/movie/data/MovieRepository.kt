package com.desnyki.moviedb.movie.data

import androidx.lifecycle.distinctUntilChanged
import com.desnyki.moviedb.data.resultLiveData
import com.desnyki.moviedb.movie.data.MovieDao
import com.desnyki.moviedb.movie.data.MovieRemoteSource

class MovieRepository constructor(private val dao: MovieDao,
                                  private val remoteSource: MovieRemoteSource
) {

    val movies by lazy {
        resultLiveData(
            databaseQuery = { dao.getMovies() },
            networkCall = { remoteSource.fetchMovies() },
            saveCallResult = { dao.insertAll(it.results) }
        )
    }

    fun movie(id: Int) = resultLiveData(
        databaseQuery = { dao.getMovie(id) },
        networkCall = { remoteSource.fetchMovie(id) },
        saveCallResult = { dao.insert(it) }
        ).distinctUntilChanged()

}