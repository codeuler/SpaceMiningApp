package com.example.spacemining

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.widget.ArrayAdapter;
import androidx.navigation.findNavController
import com.example.spacemining.databinding.FragmentVisualizacionBinding

class VisualizacionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentVisualizacionBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_visualizacion,container,false)
        //var spinnerTipoGrafico = binding.tipoGraficoSpinner
        val spinnerVisualizacion = binding.visualizacionSpinner

        val spinnerTipoGrafico = binding.tipoGraficoSpinner

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.tipos_graficos,
            android.R.layout.simple_spinner_item
        ).also{
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTipoGrafico.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.visualización,
            android.R.layout.simple_spinner_item
        ).also{
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerVisualizacion.adapter = adapter
        }

        /*
        spinnerTipoGrafico.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedItem = items[position]
                //Toast.makeText(this@VisualizacionFragment,selectedItem,Toast.LENGTH_SHORT).show()
            }

         */

        binding.predecirText.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_visualizacionFragment_to_mineriaFragment)
        }

        return binding.root
    }
}