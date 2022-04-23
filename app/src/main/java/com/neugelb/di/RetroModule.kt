package com.neugelb.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.neugelb.MyApplication
import com.neugelb.R
import com.neugelb.adapter.AutoCompleteAdapter
import com.neugelb.adapter.FavoriteAdapter
import com.neugelb.adapter.MovieListAdapter
import com.neugelb.config.PREFER_NAME
import com.neugelb.data.Api
import com.neugelb.data.ApiService
import com.neugelb.data.SessionManager
import com.neugelb.data.model.Movie
import com.neugelb.data.repository.Repository
import com.neugelb.room.database.AppDatabase
import com.neugelb.room.database.AppDatabase.Companion.MIGRATION_1
import com.neugelb.room.database.CachingRepository
import com.neugelb.room.database.DataDao
import com.neugelb.room.database.DataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@Module
open class RetroModule {

    @Singleton
    @Provides
    open fun provideApi(): Api = ApiService.getClient()

    @Provides
    @Singleton
    open fun getRepository(): Repository {
        return Repository(provideApi())
    }

    @Provides
    fun provideMovieListAdapter(): MovieListAdapter =
        MovieListAdapter()

    @Provides
    fun provideFavoriteAdapter(): FavoriteAdapter =
        FavoriteAdapter()

    @Provides
    fun provideListData() = ArrayList<Movie>()

    @Provides
    fun provideAutoListAdapter(list: ArrayList<Movie>): AutoCompleteAdapter = AutoCompleteAdapter(
        MyApplication.instance.applicationContext,
        R.layout.custom_autocomplete_layout, R.id.autoCompleteTextView, list
    )

    @Provides
    fun provideSharedPref(): SharedPreferences {
        return MyApplication.instance.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    open fun getSession(): SessionManager =
        SessionManager(provideSharedPref(), provideSharedPref().edit())


    @Singleton
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        return Room.databaseBuilder(
            MyApplication.instance.applicationContext,
            AppDatabase::class.java,
            "cashing-db"
        ).addMigrations(MIGRATION_1).build()
    }

    @Singleton
    @Provides
    open fun providesCashingDao(cashingDatabase: AppDatabase): DataDao {
        return cashingDatabase.dataDao()
    }

    @DelicateCoroutinesApi
    @Singleton
    @Provides
    open fun cashingRepository(dao: DataDao): CachingRepository {
        return DataSource(dao)
    }
}
