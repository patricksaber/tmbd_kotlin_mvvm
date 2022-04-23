package com.neugelb

import com.neugelb.di.DaggerRetroComponent
import com.neugelb.di.RetroComponent
import com.neugelb.di.RetroModuleAndroidTest


class MyApplicationAndroidTest : MyApplication() {

    override fun getRetroComponent(): RetroComponent = DaggerRetroComponent.builder()
        .application(this)
        .retroModule(RetroModuleAndroidTest())
        .build()

}