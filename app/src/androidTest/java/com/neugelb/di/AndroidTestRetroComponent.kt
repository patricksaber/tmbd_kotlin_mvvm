package com.neugelb.di


import com.neugelb.MyApplication
import com.neugelb.data.repository.RepositoryTest
import com.neugelb.viewModel.activity.MainActivityViewModelTest
import com.neugelb.viewModel.fragment.FavoriteViewModelTest
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [RetroModule::class, ViewModelModule::class, AndroidSupportInjectionModule::class, ActivityModule::class, FragmentModule::class])
interface AndroidTestRetroComponent : RetroComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder

        @BindsInstance
        fun retroModule(retroModule: RetroModule): Builder

        fun build(): AndroidTestRetroComponent
    }

    fun inject(appRepositoryInjectTest: MainActivityViewModelTest)
    fun inject(favoriteViewModel: FavoriteViewModelTest)
    fun inject(repositoryTest: RepositoryTest)

}