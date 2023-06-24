package com.ramazantiftik.advancedartbook.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ramazantiftik.advancedartbook.model.Art

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Art)

    @Delete
    suspend fun deleteArt(art: Art)

    @Query("SELECT * FROM arts")
    fun getAllArts(): LiveData<List<Art>>

}