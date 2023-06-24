package com.ramazantiftik.advancedartbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramazantiftik.advancedartbook.model.Art

@Database(entities = [Art::class], version = 1)
abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao(): ArtDao
}