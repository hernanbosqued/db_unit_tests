package com.hernanbosqued.db_unit_tests.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ParameterEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "my_database"

        fun getDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
    }

    abstract fun parameterDao(): ParameterDao
}

