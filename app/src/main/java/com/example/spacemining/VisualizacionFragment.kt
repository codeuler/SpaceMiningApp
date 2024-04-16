package com.example.spacemining

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import android.widget.ArrayAdapter;
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.spacemining.databinding.FragmentVisualizacionBinding

class VisualizacionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentVisualizacionBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_visualizacion,container,false)

        val spinnerVisualizacion = binding.visualizacionSpinner
        val spinnerTipoGrafico = binding.tipoGraficoSpinner
        val consulta = mutableListOf("T","0","A")
        var listaVisualizacion = mutableListOf("")
        val itemsTipoGrafico = resources.getStringArray(R.array.tipos_graficos)

        Glide.with(this.requireContext()).load(
            "https://space-mining-api.onrender.com/data/images/get?orbita=T&grafico=0&ejes=A"
        ).placeholder(R.mipmap.loading_icon).error(R.mipmap.error).into(binding.visualizacionImageView)
        binding.tituloText.text = "Dispersión (órbita) del Apogee - Period"

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.tipos_graficos,
            R.layout.spinner_visual
        ).also{
            adapter -> adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
            spinnerTipoGrafico.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.dispersion_orbita,
            R.layout.spinner_visual
        ).also{
            adapter -> adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
            spinnerVisualizacion.adapter = adapter
        }

        val adapterDispersionOrbita:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.dispersion_orbita,
            R.layout.spinner_visual
        ).also{
                adapter -> adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
        }

        val adapterDispersionNorbita:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.dispersion_noorbita,
            R.layout.spinner_visual
        ).also{
                adapter -> adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
        }
        val adapterDistribucionOrbita:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.distribucion_orbita,
            R.layout.spinner_visual
        ).also{
                adapter -> adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
        }

        val adapterDistribucionNorbita:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.distribucion_noorbita,
            R.layout.spinner_visual
        ).also{
                adapter -> adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
        }

        val adapterHistogramaNoorbita:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.histograma_noorbita,
            R.layout.spinner_visual
        ).also{
                adapter -> adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
        }
        val adapterHistogramaorbita:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.histograma_orbita,
            R.layout.spinner_visual
        ).also{
                adapter -> adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
        }

        val adapterCircular:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.circular,
            R.layout.spinner_visual
        ).also{
                adapter -> adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
        }

        spinnerTipoGrafico.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(itemsTipoGrafico[position]){
                    "Dispersión (no órbita)" -> {
                        spinnerVisualizacion.adapter = adapterDispersionNorbita
                        consulta[0]="F"
                        consulta[1]="0"
                        consulta[2]="A"
                        listaVisualizacion = resources.getStringArray(R.array.dispersion_noorbita).toMutableList()
                    }
                    "Dispersión (órbita)" -> {
                        spinnerVisualizacion.adapter = adapterDispersionOrbita
                        consulta[0]="T"
                        consulta[1]="0"
                        consulta[2]="A"

                        listaVisualizacion = resources.getStringArray(R.array.dispersion_orbita).toMutableList()
                    }
                    "Distribución (órbita)" -> {
                        spinnerVisualizacion.adapter = adapterDistribucionOrbita
                        consulta[0]="T"
                        consulta[2]="A"
                        consulta[1]="1"
                        listaVisualizacion = resources.getStringArray(R.array.distribucion_orbita).toMutableList()
                    }
                    "Distribución (no órbita)" -> {
                        spinnerVisualizacion.adapter = adapterDistribucionNorbita
                        consulta[0]="F"
                        consulta[2]="A"
                        consulta[1]="1"
                        listaVisualizacion = resources.getStringArray(R.array.distribucion_noorbita).toMutableList()
                    }
                    "Circular (órbita)" -> {
                        spinnerVisualizacion.adapter = adapterCircular
                        consulta[0]="T"
                        consulta[1]="2"
                        consulta[2]="A"
                        listaVisualizacion = resources.getStringArray(R.array.circular).toMutableList()
                    }
                    "Circular (no órbita)" -> {
                        spinnerVisualizacion.adapter = adapterCircular
                        consulta[0]="F"
                        consulta[1]="2"
                        consulta[2]="A"
                        listaVisualizacion = resources.getStringArray(R.array.dispersion_orbita).toMutableList()
                    }
                    "Histograma (no órbita)" -> {
                        spinnerVisualizacion.adapter = adapterHistogramaNoorbita
                        consulta[0]="F"
                        consulta[2]="A"
                        consulta[1]="3"
                        listaVisualizacion = resources.getStringArray(R.array.histograma_noorbita).toMutableList()
                    }
                    "Histograma (órbita)" -> {
                        spinnerVisualizacion.adapter = adapterHistogramaorbita
                        consulta[0]="T"
                        consulta[2]="A"
                        consulta[1]="3"
                        listaVisualizacion = resources.getStringArray(R.array.histograma_orbita).toMutableList()
                    }
                }
            }
        }

        spinnerVisualizacion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(listaVisualizacion[position]){
                    "Apogee - Period" ->{
                        consulta[2]="A"
                    }
                    "Apogee - Perigee" ->{
                        consulta[2]="B"
                    }
                    "Apogee - Inclination" ->{
                        consulta[2]="C"
                    }
                    "Period - Perigee" ->{
                        consulta[2]="D"
                    }
                    "Period - Inclination" ->{
                        consulta[2]="E"
                    }
                    "Perigee - Inclination" ->{
                        consulta[2]="F"
                    }
                    "Days_in_Orbit - Apogee" ->{
                        consulta[2]="G"
                    }
                    "Days_in_Orbit - Period" ->{
                        consulta[2]="H"
                    }
                    "Days_in_Orbit - Perigee" ->{
                        consulta[2]="I"
                    }
                    "Days_in_Orbit - Inclination" ->{
                        consulta[2]="J"
                    }
                    "RCS_SIZE - Apogee" ->{
                        consulta[2]="A"
                    }
                    "RCS_SIZE - Period" ->{
                        consulta[2]="B"
                    }
                    "RCS_SIZE - Perigee" ->{
                        consulta[2]="C"
                    }
                    "RCS_SIZE - Inclination" ->{
                        consulta[2]="D"
                    }
                    "RCS_SIZE - Days in Orbit" ->{
                        consulta[2]="E"
                    }
                    "Apogee"->{
                        consulta[2]="A"
                    }
                    "Period"->{
                        consulta[2]="B"
                    }
                    "Perigee"->{
                        consulta[2]="C"
                    }
                    "Inclination"->{
                        consulta[2]="D"
                    }
                    "Days_in_Orbit"->{
                        consulta[2]="E"
                    }
                }
            }
        }

        binding.predecirText.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_visualizacionFragment_to_mineriaFragment)
        }

        binding.graficarButton.setOnClickListener{
            val titulo = "Grafico ${binding.tipoGraficoSpinner.selectedItem} de ${binding.visualizacionSpinner.selectedItem}"
            binding.tituloText.text = titulo
            val url = "https://space-mining-api.onrender.com/data/images/get?orbita=${consulta[0]}&grafico=${consulta[1]}&ejes=${consulta[2]}"
            Glide.with(this.requireContext()).load(url).placeholder(R.mipmap.loading_icon
            ).error(R.mipmap.error).into(binding.visualizacionImageView)
        }

        return binding.root
    }
}