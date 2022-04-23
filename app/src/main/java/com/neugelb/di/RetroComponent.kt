package com.neugelb.di

import com.neugelb.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [RetroModule::class, ViewModelModule::class, AndroidSupportInjectionModule::class, ActivityModule::class, FragmentModule::class])
interface RetroComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder

        @BindsInstance
        fun retroModule(retroModule: RetroModule): Builder

        fun build(): RetroComponent
    }

    fun inject(into: MyApplication)

}