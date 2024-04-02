package com.example.spacemining

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.spacemining.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentMenuBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_menu,container,false)
        binding.sttInSpaceBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_menuFragment_to_stufInSpaceFragment)
        }
        binding.rastreoEspacialBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_menuFragment_to_visualizacionFragment)
        }
        binding.lexiconBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_menuFragment_to_bibliotecaFragment2) }

        //val actividadContenedora = requireActivity()

        //Poner un fondo degradado a las letras de los botones
        //binding.frame1.setLayerPaint().setMaskFilter(BlurMaskFilter(50F,BlurMaskFilter.Blur.SOLID))
        //binding.sttInSpaceBtn.background
        //binding.sttInSpaceBtn.setTextColor(Color.WHITE)

        binding.sttInSpaceBtn.setShadowLayer(20F,0F,0F, Color.WHITE)
        binding.lexiconBtn.setShadowLayer(20F,0F,0F, Color.WHITE)
        binding.rastreoEspacialBtn.setShadowLayer(20F,0F,0F, Color.WHITE)



        // Modificar vistas dentro de la actividad
        //actividadContenedora.findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE

        return binding.root
    }

}
