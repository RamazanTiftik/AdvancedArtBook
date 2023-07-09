package com.ramazantiftik.advancedartbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramazantiftik.advancedartbook.model.Art
import com.ramazantiftik.advancedartbook.model.ImageResponse
import com.ramazantiftik.advancedartbook.repo.ArtRepositoryInterface
import com.ramazantiftik.advancedartbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {

    //Art Fragment

    val artList=repository.getArt()


    //Image API Fragment


    private val images=MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage=MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage


    // Art Details Fragment

    private var insertArtMsg=MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg= MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch{
        repository.deleteAll(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertAll(art)
    }

    fun makeArt(name: String, artistName: String, year: String){ // saveButton func -> save art

        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error(null,"Enter name, artist and year!!!"))
            return
        }

        val year= try {
            year.toInt()
        } catch (e: Exception){
            insertArtMsg.postValue(Resource.error(null,"Year should be number"))
            return
        }

        val art=Art(name,artistName,year,selectedImageUrl.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchString: String){
        if (searchString.isEmpty()){
            return
        }

        images.value=Resource.loading(null)
        viewModelScope.launch {
            val response=repository.imageSearch(searchString)
            images.value=response
        }

    }


}