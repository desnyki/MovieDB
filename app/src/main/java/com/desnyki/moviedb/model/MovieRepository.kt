package com.desnyki.moviedb.model

import androidx.lifecycle.distinctUntilChanged
import com.desnyki.moviedb.data.resultLiveData

class MovieRepository constructor(private val dao: MovieDao,
                                              private val remoteSource: MovieRemoteSource) {

    val movies = resultLiveData(
        databaseQuery = { dao.getMovies() },
        networkCall = { remoteSource.fetchMovies() },
        saveCallResult = { dao.insertAll(it.results) }
        )

    fun movie(id: Int) = resultLiveData(
        databaseQuery = { dao.getMovie(id) },
        networkCall = { remoteSource.fetchMovie(id) },
        saveCallResult = { dao.insert(it) }
        ).distinctUntilChanged()

}