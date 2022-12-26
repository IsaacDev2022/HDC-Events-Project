package com.hdceventsapp20.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hdceventsapp20.model.dao.eventDAO.EventDAO
import com.hdceventsapp20.model.dao.userDAO.UserDAO
import com.hdceventsapp20.model.entities.eventEntity.Event
import com.hdceventsapp20.model.entities.userEntity.User

@Database(entities = arrayOf(Event::class, User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val eventDAO: EventDAO
    abstract val userDAO: UserDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "hdcEvents_db"
                    ).build()
                }

                return instance
            }
        }
    }
}