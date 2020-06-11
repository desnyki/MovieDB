package com.desnyki.moviedb

import android.app.Application
import com.desnyki.moviedb.di.DaggerAppComponent


class App : Application(){
        val appComponent = DaggerAppComponent.builder().application(this).build()
}