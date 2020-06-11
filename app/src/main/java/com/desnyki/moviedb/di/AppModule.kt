package com.desnyki.moviedb.di

import android.app.Application
import com.desnyki.moviedb.BuildConfig
import com.desnyki.moviedb.api.AuthInterceptor
import com.desnyki.moviedb.api.MovieService
import com.desnyki.moviedb.data.AppDatabase
import com.desnyki.moviedb.movie.data.MovieRemoteSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class, NetworkModule::class])
 class AppModule {

    @Singleton
    @Provides
    fun provideMovieService(okHttpClient: OkHttpClient,
                           converterFactory: GsonConverterFactory
    ) = provideService(okHttpClient, converterFactory, MovieService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteSource(movieService: MovieService)
            = MovieRemoteSource(movieService)

//    @MovieAPI
//    @Provides
//    fun providePrivateOkHttpClient(
//        upstreamClient: OkHttpClient
//    ): OkHttpClient {
//        return upstreamClient.newBuilder()
//            .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN)).build()
//    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MovieService.ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(okHttpClient: OkHttpClient,
                                   converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okHttpClient, converterFactory).create(clazz)
    }

}