package com.ramazantiftik.advancedartbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ramazantiftik.advancedartbook.R
import com.ramazantiftik.advancedartbook.databinding.FragmentArtsBinding

class ArtFragment : Fragment(R.layout.fragment_arts) {

    //viewBinding
    private var _binding: FragmentArtsBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentArtsBinding.inflate(inflater,container,false)
        val view=binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}