package com.example.spacemining

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.spacemining.databinding.FragmentStufInSpaceBinding

class StufInSpaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentStufInSpaceBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_stuf_in_space,container,false)

        val url = "https://"+binding.linkStuff.text.toString()

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)

        binding.linkStuff.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        return binding.root
    }

}