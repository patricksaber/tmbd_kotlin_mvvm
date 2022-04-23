@file:Suppress("unused")

package com.neugelb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neugelb.utils.ViewModelFactory
import com.neugelb.viewmodel.activity.DetailActivityViewModel
import com.neugelb.viewmodel.activity.LauncherViewModel
import com.neugelb.viewmodel.activity.LoginViewModel
import com.neugelb.viewmodel.activity.MainActivityViewModel
import com.neugelb.viewmodel.fragment.FavoriteViewModel
import com.neugelb.viewmodel.fragment.HomeViewModel
import com.neugelb.viewmodel.fragment.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel::class)
    protected abstract fun launcherViewModel(viewModel: LauncherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    protected abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    protected abstract fun mainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailActivityViewModel::class)
    protected abstract fun detailActivityViewModel(viewModel: DetailActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    protected abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    protected abstract fun favoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    protected abstract fun settingsViewModel(viewModel: SettingsViewModel): ViewModel


}