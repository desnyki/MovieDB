package com.desnyki.moviedb.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.desnyki.moviedb.data.AppDatabase
import com.desnyki.moviedb.movie.data.MovieModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class SeedDatabaseWorker(
        context: Context,
        workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {

            try {
                applicationContext.assets.open("movies.json").use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val type = object : TypeToken<List<MovieModel>>() {}.type
                        val list: List<MovieModel> = Gson().fromJson(jsonReader, type)

                        AppDatabase.getInstance(applicationContext).movieDao().insertAll(list)

                        Result.success()
                    }
                }
            } catch (e: Exception) {
                Log.e("SeedDatabaseWorker", "Error seeding database")
                Result.failure()
            }
        }
    }
}