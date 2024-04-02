package com.example.spacemining

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.spacemining.databinding.ActivityMainBinding
import com.example.spacemining.databinding.FragmentEntryBinding

class EntryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentEntryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_entry, container, false
        )
        binding.explorarBotton.setOnClickListener {
            //Se realiza la trasición de este pantalla a menufragment
            view?.findNavController()?.navigate(R.id.action_entryFragment_to_menuFragment)
        }


        //val activityBinding = (requireActivity() as MainActivity).ActivityMainBinding



        //val actividadContenedora = requireActivity()
        // Modificar vistas dentro de la actividad
        //actividadContenedora.findViewById<TextView>(R.id.text_toolbar).text = "Cualquier cosa"

        //binding.explorarBotton.setOnClickListener(
        //    Navigation.createNavigateOnClickListener(R.id.action_entryFragment_to_menuFragment)
        //)

        return binding.root
    }
}