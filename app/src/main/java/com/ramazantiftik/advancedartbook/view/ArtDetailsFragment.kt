package com.ramazantiftik.advancedartbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ramazantiftik.advancedartbook.R
import com.ramazantiftik.advancedartbook.databinding.FragmentArtDetailsBinding
import com.ramazantiftik.advancedartbook.databinding.FragmentArtsBinding

class ArtDetailsFragment : Fragment(R.layout.fragment_art_details) {

    //viewBinding
    private var _binding: FragmentArtDetailsBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //viewBinding
        _binding= FragmentArtDetailsBinding.inflate(inflater,container,false)
        val view=binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.artDetailsImage.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback=object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}