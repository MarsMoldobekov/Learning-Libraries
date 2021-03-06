package com.example.learninglibraries

import android.app.Application
import com.example.learninglibraries.domain.database.Database
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import timber.log.Timber

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    private val cicerone: Cicerone<Router> by lazy { Cicerone.create() }
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        instance = this
        Database.create(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}