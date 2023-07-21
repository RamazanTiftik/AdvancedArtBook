package com.ramazantiftik.advancedartbook.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramazantiftik.advancedartbook.model.Art
import com.ramazantiftik.advancedartbook.model.ImageResponse
import com.ramazantiftik.advancedartbook.repo.ArtRepositoryInterface
import com.ramazantiftik.advancedartbook.util.Resource

class FakeArtRepository : ArtRepositoryInterface {

    private val arts= mutableListOf<Art>()
    private val artsLiveData=MutableLiveData<List<Art>>(arts)

    override suspend fun insertAll(art: Art) {
        arts.add(art)
    }

    override suspend fun deleteAll(art: Art) {
        arts.remove(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun imageSearch(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }

}