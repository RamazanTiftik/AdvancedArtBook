package com.ramazantiftik.advancedartbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramazantiftik.advancedartbook.R
import com.ramazantiftik.advancedartbook.adapter.ArtRecyclerAdapter
import com.ramazantiftik.advancedartbook.databinding.FragmentArtsBinding
import com.ramazantiftik.advancedartbook.viewmodel.ArtViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class ArtFragment @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter
) : Fragment(R.layout.fragment_arts) {

    //viewBinding
    private var _binding: FragmentArtsBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //initialize
        viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        //viewBinding
        _binding= FragmentArtsBinding.inflate(inflater,container,false)
        val view=binding.root

        //recycler adapter
        binding.recyclerViewArt.adapter=artRecyclerAdapter
        binding.recyclerViewArt.layoutManager=LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        //fab button
        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }

        subscribeToObservers()

        return view
    }


    private val swipeCallBack=object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition=viewHolder.layoutPosition
            val selectedArt=artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }


    private fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts=it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}