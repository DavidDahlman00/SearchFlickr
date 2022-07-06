package com.example.searchflickir.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Settings::class], version = 2, exportSchema = false)
abstract class SettingsRoomDatabase: RoomDatabase() {

    abstract fun settingsDao() : SettingsDao

    private class SettingsDatabaseCallback(
        private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.settingsDao())
                }
            }
        }

        suspend fun populateDatabase(settingsDao: SettingsDao){

            val settings = Settings(1, count = 1)
            settingsDao.insert(settings)
        }

    }

    companion object {

        @Volatile
        private var INSTANCE : SettingsRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope) : SettingsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    SettingsRoomDatabase::class.java,
                    "settings_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(SettingsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}