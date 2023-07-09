package com.ramazantiftik.advancedartbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.ramazantiftik.advancedartbook.R
import com.ramazantiftik.advancedartbook.databinding.FragmentArtDetailsBinding
import com.ramazantiftik.advancedartbook.databinding.FragmentArtsBinding
import com.ramazantiftik.advancedartbook.util.Status
import com.ramazantiftik.advancedartbook.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_art_details) {

    //viewBinding
    private var _binding: FragmentArtDetailsBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentArtDetailsBinding.inflate(inflater,container,false)
        val view=binding.root

        binding.artDetailsImage.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        //initialize
        viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        subscribeToObserver()

        val callback=object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        //save button
        binding.saveButton.setOnClickListener {
            viewModel.makeArt(binding.artDetailsArtName.toString(),
                binding.artDetailsArtistName.toString(),
                binding.artDetailsYearText.toString())
        }

        return view
    }

    private fun subscribeToObserver(){

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {url ->
            glide.load(url).into(binding.artDetailsImage)
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status) {

                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}