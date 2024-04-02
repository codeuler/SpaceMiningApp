package com.example.spacemining

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.spacemining.databinding.FragmentMenuBinding
import com.example.spacemining.databinding.FragmentStufInSpaceBinding

class StufInSpaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentStufInSpaceBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_stuf_in_space,container,false)
        return binding.root
    }

}