package com.neugelb.di

import com.neugelb.ui.fragment.HomeFragment
import com.neugelb.ui.fragment.FavoriteFragment
import com.neugelb.ui.fragment.SettingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingFragment(): SettingFragment

}
