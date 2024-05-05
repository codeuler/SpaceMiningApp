package com.example.spacemining

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.spacemining.databinding.FragmentEntryBinding

/**
 * Fragmento de entrada que muestra un botón para explorar y navegar al menú principal.
 */
class EntryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño para este fragmento utilizando DataBinding
        val binding: FragmentEntryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_entry, container, false
        )

        // Configura el clic del botón "Explorar" para navegar al menú principal
        binding.explorarBotton.setOnClickListener {
            // Realiza la transición de este fragmento al fragmento del menú principal
            view?.findNavController()?.navigate(R.id.action_entryFragment_to_menuFragment)
        }

        return binding.root
    }
}
