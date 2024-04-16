package com.example.spacemining

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import android.widget.ArrayAdapter;
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
        val url = "https://space-mining-api.onrender.com/data/images/get?orbita=T&grafico=0&ejes=A"

        Glide.with(this.requireContext()).load(url).timeout(100000).thumbnail(Glide.with(
            this.requireContext()).load(R.drawable.imageloading)).error(R.mipmap.error
            ).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(
            binding.visualizacionImageView)

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
                consulta[2] = "A"
                when(itemsTipoGrafico[position]){
                    "Dispersión (no órbita)" -> {
                        spinnerVisualizacion.adapter = adapterDispersionNorbita
                        consulta[0]="F"
                        consulta[1]="0"
                        listaVisualizacion = resources.getStringArray(R.array.dispersion_noorbita).toMutableList()
                    }
                    "Dispersión (órbita)" -> {
                        spinnerVisualizacion.adapter = adapterDispersionOrbita
                        consulta[0]="T"
                        consulta[1]="0"
                        listaVisualizacion = resources.getStringArray(R.array.dispersion_orbita).toMutableList()
                    }
                    "Distribución (órbita)" -> {
                        spinnerVisualizacion.adapter = adapterDistribucionOrbita
                        consulta[0]="T"
                        consulta[1]="1"
                        listaVisualizacion = resources.getStringArray(R.array.distribucion_orbita).toMutableList()
                    }
                    "Distribución (no órbita)" -> {
                        spinnerVisualizacion.adapter = adapterDistribucionNorbita
                        consulta[0]="F"
                        consulta[1]="1"
                        listaVisualizacion = resources.getStringArray(R.array.distribucion_noorbita).toMutableList()
                    }
                    "Circular (órbita)" -> {
                        spinnerVisualizacion.adapter = adapterCircular
                        consulta[0]="T"
                        consulta[1]="2"
                        listaVisualizacion = resources.getStringArray(R.array.circular).toMutableList()
                    }
                    "Circular (no órbita)" -> {
                        spinnerVisualizacion.adapter = adapterCircular
                        consulta[0]="F"
                        consulta[1]="2"
                        listaVisualizacion = resources.getStringArray(R.array.dispersion_orbita).toMutableList()
                    }
                    "Histograma (no órbita)" -> {
                        spinnerVisualizacion.adapter = adapterHistogramaNoorbita
                        consulta[0]="F"
                        consulta[1]="3"
                        listaVisualizacion = resources.getStringArray(R.array.histograma_noorbita).toMutableList()
                    }
                    "Histograma (órbita)" -> {
                        spinnerVisualizacion.adapter = adapterHistogramaorbita
                        consulta[0]="T"
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
                consulta[2] = when(listaVisualizacion[position]){
                    "Apogee - Period","RCS_SIZE - Apogee","Apogee","RCS_SIZE" ->{
                        "A"
                    }
                    "Apogee - Perigee","RCS_SIZE - Period","Period" ->{
                        "B"
                    }
                    "Apogee - Inclination","RCS_SIZE - Perigee","Perigee" ->{
                        "C"
                    }
                    "Period - Perigee","RCS_SIZE - Inclination","Inclination" ->{
                        "D"
                    }
                    "Period - Inclination","RCS_SIZE - Days_in_Orbit","Days_in_Orbit" ->{
                        "E"
                    }
                    "Perigee - Inclination" ->{
                        "F"
                    }
                    "Days_in_Orbit - Apogee" ->{
                        "G"
                    }
                    "Days_in_Orbit - Period" ->{
                        "H"
                    }
                    "Days_in_Orbit - Perigee" ->{
                        "I"
                    }
                    else -> {"J"}
                }
            }
        }

        binding.predecirText.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_visualizacionFragment_to_mineriaFragment)
        }

        binding.graficarButton.setOnClickListener{
            val titulo = "Grafico ${binding.tipoGraficoSpinner.selectedItem} de ${binding.visualizacionSpinner.selectedItem}"
            binding.tituloText.text = titulo
            val direccion = "https://space-mining-api.onrender.com/data/images/get?orbita=${consulta[0]}&grafico=${consulta[1]}&ejes=${consulta[2]}"
            Glide.with(this.requireContext()).load(direccion).timeout(100000).thumbnail(
                Glide.with(this.requireContext()).load(R.drawable.imageloading)).error(
                R.mipmap.error).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true
                ).into(binding.visualizacionImageView)
        }
        return binding.root
    }
}