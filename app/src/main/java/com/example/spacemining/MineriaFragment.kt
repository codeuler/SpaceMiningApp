package com.example.spacemining

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.spacemining.databinding.FragmentMineriaBinding
import com.example.spacemining.interfaces.RowsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            inflater, R.layout.fragment_mineria, container, false
        )

        binding.buttonPredecir.setOnClickListener { calcularPrediccion() }
        getRows()

        return binding.root
    }

    private fun calcularPrediccion() {
        val apogee: Double
        val perigee: Double
        if (binding.editTextApogee.text.isEmpty() || binding.editTextPerigee.text.isEmpty()) {
            Toast.makeText(context, R.string.advertencia_text, Toast.LENGTH_SHORT).show()
            binding.valorPrediccion.text = getString(R.string.nada)
        } else {
            apogee = (binding.editTextApogee.text).toString().toDouble()
            perigee = (binding.editTextPerigee.text).toString().toDouble()
            if (apogee <= perigee) {
                Toast.makeText(context, R.string.advertencia_apogee, Toast.LENGTH_SHORT).show()
                binding.valorPrediccion.text = getString(R.string.nada)
            } else {
                val prediccion: Double =
                    apogee * coeficienteApogee + perigee * coeficientePerigee + interceptoY
                binding.valorPrediccion.text = prediccion.toString()
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://space-mining-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRows() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(RowsApi::class.java)
                .getRowsRandom("data/files/rows/5/data-in-orbit.csv")

            val datos = call.body()
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    var cadena = ""
                    if (datos != null) {
                        for (i in datos) {
                            cadena += "id: ${i.objectId}  - apogee ${i.apogee} - perigee ${i.perigee} perido ${i.period} \n"
                        }
                        val s = "textViewPrueba"
                        binding.textViewPrueba.text = cadena
                    } else {
                        Toast.makeText(context, "Datos nullos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}