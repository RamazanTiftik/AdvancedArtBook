package com.ramazantiftik.advancedartbook.repo

import androidx.lifecycle.LiveData
import com.ramazantiftik.advancedartbook.model.Art
import com.ramazantiftik.advancedartbook.model.ImageResponse
import com.ramazantiftik.advancedartbook.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertAll(art: Art)

    suspend fun deleteAll(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun imageSearch(imageString: String) : Resource<ImageResponse>

}