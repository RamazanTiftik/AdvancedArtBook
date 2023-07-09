package com.ramazantiftik.advancedartbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ramazantiftik.advancedartbook.R
import com.ramazantiftik.advancedartbook.adapter.ImageRecyclerAdapter
import com.ramazantiftik.advancedartbook.databinding.FragmentArtDetailsBinding
import com.ramazantiftik.advancedartbook.databinding.FragmentImageApiBinding
import com.ramazantiftik.advancedartbook.util.Status
import com.ramazantiftik.advancedartbook.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    //viewBinding
    private var _binding: FragmentImageApiBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentImageApiBinding.inflate(inflater,container,false)
        val view=binding.root

        //initialize
        viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        subscribeToObserve()
        searchBarFunc()


        //adapter
        binding.imageViewRecyclerView.adapter=imageRecyclerAdapter
        binding.imageViewRecyclerView.layoutManager=GridLayoutManager(requireContext(),3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

        return view
    }

    private fun subscribeToObserve(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {

            when(it.status){

                Status.SUCCESS -> {
                    val urls= it.data?.hits?.map {
                        it.previewUrl
                    }

                    imageRecyclerAdapter.images=urls ?: listOf()
                    binding.progressBar.visibility=View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility=View.GONE
                }

                Status.LOADING -> {
                    binding.progressBar.visibility=View.VISIBLE
                }

            }

        })
    }


    private fun searchBarFunc(){
        var job: Job?= null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job=lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}