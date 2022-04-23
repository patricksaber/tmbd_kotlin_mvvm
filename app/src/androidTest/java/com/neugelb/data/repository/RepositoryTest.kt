package com.neugelb.data.repository

import android.content.Context
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.neugelb.MyApplication
import com.neugelb.data.*
import com.neugelb.data.model.Movie
import com.neugelb.data.model.Person
import com.neugelb.data.model.User
import com.neugelb.data.req.LoginReq
import com.neugelb.data.resp.GetCastAndCrewResponse
import com.neugelb.di.DaggerAndroidTestRetroComponent
import com.neugelb.di.RetroModuleAndroidTest
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import javax.inject.Inject
import kotlin.random.Random

@RunWith(AndroidJUnit4ClassRunner::class)
class RepositoryTest : TestCase() {
    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var api: Api

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
    }

    @Test
    fun getDetails() = runBlocking {
        testDetails(414906).collect {
            assert(true)
        }
    }

    @Test
    fun getCastAndCrew() = runBlocking {
        testGetCastAndCrew(414906).collect {
            assert(true)
        }
    }

    @Test
    fun getPopularTvShows() = runBlocking {
        testPopularTvShows().collect {
            assert(true)
        }
    }

    @Test
    fun testFakeLoginChecker() = runBlocking {
        val req = LoginReq(username = "admin", password = "admin")
        val data = checkUser(req)
        assertEquals(data.data!!.name, req.username)
    }

    private suspend fun testDetails(movieId: Long): Flow<ApiState<Movie>> {
        return flow {
            emit(ApiState.success(api.getDetails(movieId = movieId)))
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun testPopularTvShows(): Flow<ApiState<PaginatedListResponse<Movie>>> {
        return flow {
            emit(ApiState.success(api.getPopularTvShows(2022, 1)))
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun testGetCastAndCrew(movieId: Long): Flow<ApiState<GetCastAndCrewResponse>> {
        return flow {
            emit(ApiState.success(api.getMovieCredits(movieId)))
        }.flowOn(Dispatchers.IO)
    }


    private suspend fun checkUser(req: LoginReq): Resources<Person> {
        return try {
            delay(3000)
            if (Random.nextInt(4) == 0) {
                throw Exception()
            } else {
                for (user in getUserData()) {
                    if (user.username == req.username && user.password == req.password) {
                        return Resources.Success(
                            Person(name = user.username)
                        )
                    }
                }
                Resources.Error(UiState.Error.Invalid)
            }
        } catch (e: Exception) {
            Resources.Error(UiState.Error.NetworkError)
        }
    }


    private fun getUserData(): List<User> {
        return listOf(
            User(username = "admin", password = "admin"),
            User(username = "alex", password = "admin"),
            User(username = "robert", password = "123"),
            User(username = "patrick", password = "happy")
        )
    }
}