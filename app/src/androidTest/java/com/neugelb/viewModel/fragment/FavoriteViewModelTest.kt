package com.neugelb.viewModel.fragment

import android.content.Context
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.neugelb.MyApplication
import com.neugelb.data.SessionManager
import com.neugelb.di.DaggerAndroidTestRetroComponent
import com.neugelb.di.RetroModuleAndroidTest
import com.neugelb.data.repository.Repository
import com.neugelb.room.model.CachingMovie
import com.neugelb.room.database.CachingRepository
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
class FavoriteViewModelTest : TestCase() {
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
    fun testListOfFavorites() = runBlocking {
        cachingRepository.clearFavorites()
        for (i in 1..20) {
            val model = CachingMovie(0, i, 2, "test", "test test", "test", 1.0)
            cachingRepository.insertFavorite(model)
        }
        val list = cachingRepository.getFavorites()
        assertEquals(20, list.size)
    }

    @Test
    fun testCheckFavoriteById() = runBlocking {
        cachingRepository.clearFavorites()
        val model = CachingMovie(0, 10, 2, "test", "test test", "test", 1.0)
        cachingRepository.insertFavorite(model)
        val list = cachingRepository.checkFavoriteById(10)
        assertNotNull(list)
    }

    @Test
    fun testDeleteById() = runBlocking {
        cachingRepository.clearFavorites()
        val model = CachingMovie(0, 10, 2, "test", "test test", "test", 1.2)
        cachingRepository.insertFavorite(model)
        cachingRepository.deleteFavoriteById(10)
        val list = cachingRepository.checkFavoriteById(10)
        assertNull(list)
    }

}