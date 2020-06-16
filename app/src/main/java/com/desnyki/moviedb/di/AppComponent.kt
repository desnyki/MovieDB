package com.desnyki.moviedb.di

import android.app.Application
import com.desnyki.moviedb.App
import com.desnyki.moviedb.MainActivity
import com.desnyki.moviedb.movie.ui.MovieDetailFragment
import com.desnyki.moviedb.movie.ui.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: App)
    fun inject(activity: MainActivity)
    fun inject(fragment: MoviesFragment)
    fun inject(fragment: MovieDetailFragment)


}