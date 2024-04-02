package com.example.spacemining

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.spacemining.databinding.FragmentMineriaBinding

class MineriaFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMineriaBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_mineria,container,false)

        return binding.root
    }
}