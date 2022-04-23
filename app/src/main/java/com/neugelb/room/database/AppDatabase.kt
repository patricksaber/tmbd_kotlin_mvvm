package com.neugelb.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.neugelb.config.DATABASE_NAME
import com.neugelb.config.DATABASE_VERSION

import com.neugelb.room.model.CachingModel
import com.neugelb.room.model.CachingMovie

@Database(
    entities = [CachingModel::class, CachingMovie::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dataDao(): DataDao


    companion object {
        val MIGRATION_1: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE caching ADD COLUMN originalSize INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE cachingMovie ADD COLUMN originalSize INTEGER NOT NULL DEFAULT 0")
            }
        }

        @Volatile // All threads have immediate access to this property
        private var instance: AppDatabase? = null

        private val LOCK = Any() // Makes sure no threads making the same thing at the same time

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }

}