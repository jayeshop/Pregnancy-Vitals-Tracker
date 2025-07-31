package com.example.pregnancyvitalstracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vital::class], version = 1)
abstract class VitalDatabase : RoomDatabase() {
    abstract fun vitalDao(): VitalDao

    companion object {
        @Volatile private var INSTANCE: VitalDatabase? = null

        fun getInstance(context: Context): VitalDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    VitalDatabase::class.java,
                    "vital_db"
                ).build().also { INSTANCE = it }
            }
    }
}
