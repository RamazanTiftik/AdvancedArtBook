package com.ramazantiftik.advancedartbook.repo

import androidx.lifecycle.LiveData
import com.ramazantiftik.advancedartbook.api.RetrofitAPI
import com.ramazantiftik.advancedartbook.model.Art
import com.ramazantiftik.advancedartbook.model.ImageResponse
import com.ramazantiftik.advancedartbook.roomdb.ArtDao
import com.ramazantiftik.advancedartbook.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {

    override suspend fun insertAll(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteAll(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.getAllArts()
    }

    override suspend fun imageSearch(imageString: String): Resource<ImageResponse> {
        return try {

            val response=retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(null,"Error... No Data!") // if body is empty
            } else {
                Resource.error(null,"Error")
            }

        } catch (e: Exception){
            Resource.error(null,"No Data!")
        }
    }
}