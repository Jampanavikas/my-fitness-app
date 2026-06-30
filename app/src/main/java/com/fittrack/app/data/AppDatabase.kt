package com.fittrack.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fittrack.app.data.dao.DayTaskDao
import com.fittrack.app.data.dao.WeightDao
import com.fittrack.app.data.entity.DayTask
import com.fittrack.app.data.entity.WeightEntry

@Database(
    entities = [DayTask::class, WeightEntry::class],
    version = 3,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayTaskDao(): DayTaskDao
    abstract fun weightDao(): WeightDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fittrack.db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
    }
}
