package com.example.spacemining

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacemining.api.RowResponse
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
    private lateinit var adapter: RowAdapter
    private val datosRowModel = mutableListOf<RowResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_mineria, container, false
        )

        binding.buttonPredecir.setOnClickListener { calcularPrediccion(it) }
        getRows()

        initRecyclerview()

        return binding.root
    }

    private fun initRecyclerview() {
        adapter = RowAdapter(datosRowModel)
        binding.rvRows.layoutManager = LinearLayoutManager(context)
        binding.rvRows.adapter = adapter

    }

    private fun calcularPrediccion(view: View) {
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
        hideKeyBoard(view)
    }

    private fun hideKeyBoard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
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
                .getRowsRandom("data/files/rows/6/data-in-orbit.csv")

            val listaJson = call.body()
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    val rows = listaJson?: emptyList()
                    datosRowModel.clear()
                    datosRowModel.addAll(rows)
                    adapter.notifyDataSetChanged()
                    binding.tablePrediccion.visibility = View.VISIBLE
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(context, "Error al importar datos del servidor", Toast.LENGTH_SHORT).show()
    }
}