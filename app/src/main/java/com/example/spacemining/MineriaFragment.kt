package com.example.spacemining

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.spacemining.databinding.FragmentMineriaBinding

class MineriaFragment : Fragment() {

    private val coeficienteApogee = 0.01009589
    private val coeficientePerigee = 0.01030137
    private val interceptoY = 84.41740941
    private lateinit var binding: FragmentMineriaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_mineria,container,false)

        binding.buttonPredecir.setOnClickListener { calcularPrediccion() }

        return binding.root
    }

    private fun calcularPrediccion() {
        val apogee : Double
        val perigee : Double
        if(binding.editTextApogee.text.isEmpty() || binding.editTextPerigee.text.isEmpty()){
            Toast.makeText(context,R.string.advertencia_text,Toast.LENGTH_SHORT).show()
            binding.valorPrediccion.text = getString(R.string.nada)
        } else{
            apogee = (binding.editTextApogee.text).toString().toDouble()
            perigee= (binding.editTextPerigee.text).toString().toDouble()
            if(apogee <= perigee){
                Toast.makeText(context,R.string.advertencia_apogee,Toast.LENGTH_SHORT).show()
                binding.valorPrediccion.text = getString(R.string.nada)
            } else {
                val prediccion:Double = apogee * coeficienteApogee + perigee * coeficientePerigee + interceptoY
                binding.valorPrediccion.text = prediccion.toString()
            }
        }
    }
}