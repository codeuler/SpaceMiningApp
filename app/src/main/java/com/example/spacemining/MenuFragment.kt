package com.example.spacemining

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.spacemining.databinding.FragmentMenuBinding

/**
 * Fragmento que muestra el menú principal con botones para diferentes secciones.
 */
class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el diseño para este fragmento utilizando DataBinding
        val binding: FragmentMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu, container, false
        )

        // Configura el clic de los botones para navegar a diferentes fragmentos
        binding.sttInSpaceBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_menuFragment_to_stufInSpaceFragment)
        }
        binding.rastreoEspacialBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_menuFragment_to_visualizacionFragment)
        }
        binding.lexiconBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_menuFragment_to_bibliotecaFragment2)
        }

        // Configura sombras en los botones para resaltarlos visualmente
        binding.sttInSpaceBtn.setShadowLayer(20F, 0F, 0F, Color.WHITE)
        binding.lexiconBtn.setShadowLayer(20F, 0F, 0F, Color.WHITE)
        binding.rastreoEspacialBtn.setShadowLayer(20F, 0F, 0F, Color.WHITE)

        return binding.root
    }
}
