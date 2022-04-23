package com.neugelb.viewModel.activity

import android.content.Context
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.neugelb.MyApplication
import com.neugelb.data.SessionManager
import com.neugelb.data.TestObj
import com.neugelb.di.DaggerAndroidTestRetroComponent
import com.neugelb.di.RetroModuleAndroidTest
import com.neugelb.data.repository.Repository
import com.neugelb.room.database.CachingRepository
import com.neugelb.room.database.RoomResponse
import com.neugelb.viewmodel.activity.MainActivityViewModel
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityViewModelTest : TestCase() {
    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var cachingRepository: CachingRepository


    private lateinit var viewModel: MainActivityViewModel


    @Before
    public override fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        val context = Mockito.mock(Context::class.java)
        val application = Mockito.mock(MyApplication::class.java)
        Mockito.`when`(application.applicationContext).thenReturn(context)
        val retroComponent = DaggerAndroidTestRetroComponent.builder()
            .application(application)
            .retroModule(RetroModuleAndroidTest())
            .build()
        retroComponent.inject(this)
        super.setUp()
        viewModel = MainActivityViewModel(repository, sessionManager, cachingRepository)
    }


    @Test
    fun testRoom() = runBlocking {
        val people = TestObj(
            name = "Patrick",
            lastName = "ah"
        )

        viewModel.insertData(3, people, 3)

        when (val result = viewModel.getCache<TestObj>(3, 3)) {
            is RoomResponse.Success -> {
                assertEquals("Patrick", result.data?.name)
            }
            is RoomResponse.Error -> {
                assert(false)
            }
        }
    }


}