package com.neugelb.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.neugelb.MyApplicationAndroidTest


class MockTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context
    ): Application? {
        return super.newApplication(cl, MyApplicationAndroidTest::class.java.name, context)
    }
}