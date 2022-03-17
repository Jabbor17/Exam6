package com.masterandroid.exam6.manager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.datab.database.CardDao
import com.masterandroid.networkingroom.database.CardDB

@Database(entities = [CardDB::class], version = 1)
abstract class RoomManager : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {

        private var INSTANCE: RoomManager? = null

        fun getDatabase(context: Context): RoomManager? {
            if (INSTANCE == null) {
                synchronized(RoomManager::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RoomManager::class.java, "users.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

    }

}