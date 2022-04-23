@file:Suppress("LeakingThis")

package com.neugelb

import android.app.Application
import com.neugelb.di.DaggerRetroComponent
import com.neugelb.di.RetroComponent
import com.neugelb.di.RetroModule
import com.neugelb.room.database.AppDatabase
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

open class MyApplication : Application(), HasAndroidInjector {
    private lateinit var retroComponent: RetroComponent

    companion object {
        lateinit var instance: MyApplication
        lateinit var database: AppDatabase
    }

    init {
        instance = this
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        retroComponent = DaggerRetroComponent.builder()
            .application(this)
            .retroModule(RetroModule())
            .build()
        retroComponent.inject(this)
        database = AppDatabase.invoke(this)
    }

    open fun getRetroComponent(): RetroComponent = retroComponent


    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

}