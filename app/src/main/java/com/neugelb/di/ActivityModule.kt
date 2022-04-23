package com.neugelb.di

import com.neugelb.ui.activity.DetailActivity
import com.neugelb.ui.activity.LauncherActivity
import com.neugelb.ui.activity.LoginActivity
import com.neugelb.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): LauncherActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeListActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity


}