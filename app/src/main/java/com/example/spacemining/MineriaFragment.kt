package com.example.spacemining

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.spacemining.api.RowResponse
import com.example.spacemining.databinding.FragmentMineriaBinding
import com.example.spacemining.interfaces.RowsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Fragmento que muestra la actividad de minería espacial y predicciones.
 */
class MineriaFragment : Fragment() {

    // Coeficientes para el cálculo de la predicción
    private val coeficienteApogee = 0.01009589
    private val coeficientePerigee = 0.01030137
    private val interceptoY = 84.41740941

    // Variables de vista y adaptador
    private lateinit var binding: FragmentMineriaBinding
    private lateinit var adapter: RowAdapter
    private val datosRowModel = mutableListOf<RowResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el diseño para este fragmento utilizando DataBinding
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_mineria, container, false
        )

        // Configura el clic del botón de predicción
        binding.buttonPredecir.setOnClickListener { calcularPrediccion(it) }

        // Carga la imagen de carga y verifica la disponibilidad de red
        binding.imagerror.visibility = View.VISIBLE
        Glide.with(this.requireContext()).load(R.drawable.imageloading).into(
            binding.imagerror
        )
        if (isNetworkAvailable(requireContext())) {
            getRows()
        } else {
            Glide.with(this.requireContext()).load(R.mipmap.nowifi).into(
                binding.imagerror
            )
        }

        // Inicializa el RecyclerView
        initRecyclerview()

        return binding.root
    }

    // Verifica la disponibilidad de red
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapbilities = connectivityManager.activeNetwork ?: return false
            val networkInfo =
                connectivityManager.getNetworkCapabilities(networkCapbilities) ?: return false
            return networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkInfo.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    // Inicializa el RecyclerView y su adaptador
    private fun initRecyclerview() {
        adapter = RowAdapter(datosRowModel)
        binding.rvRows.layoutManager = LinearLayoutManager(context)
        binding.rvRows.adapter = adapter
    }

    // Calcula la predicción en base a los valores de apogeo y perigeo ingresados por el usuario
    private fun calcularPrediccion(view: View) {
        if (binding.editTextApogee.text.isEmpty() || binding.editTextPerigee.text.isEmpty()) {
            Toast.makeText(context, R.string.advertencia_text, Toast.LENGTH_SHORT).show()
            binding.valorPrediccion.text = getString(R.string.nada)
            return
        }
        val apogee: Double = (binding.editTextApogee.text).toString().toDouble()
        val perigee: Double = (binding.editTextPerigee.text).toString().toDouble()
        if (apogee < perigee) {
            Toast.makeText(context, R.string.advertencia_apogee, Toast.LENGTH_SHORT).show()
            binding.valorPrediccion.text = getString(R.string.nada)
        } else {
            val prediccion: Double =
                apogee * coeficienteApogee + perigee * coeficientePerigee + interceptoY
            binding.valorPrediccion.text = prediccion.toString()
        }
        hideKeyBoard(view)
    }

    // Oculta el teclado virtual después de realizar una acción
    private fun hideKeyBoard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // Configura Retrofit para realizar la llamada a la API
    private fun getRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(70, TimeUnit.SECONDS)
            .readTimeout(70, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl("http://space-mining-api.onrender.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Obtiene los datos de la API y actualiza el RecyclerView
    private fun getRows() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val call = getRetrofit().create(RowsApi::class.java)
                        .getRowsRandom("data/files/rows/6/data-in-orbit.csv")
                    val listaJson = call.body()
                    Log.d("TAG_DE_IMPRESION", listaJson.toString());
                    activity?.runOnUiThread {
                        if (call.isSuccessful) {
                            val rows = listaJson ?: emptyList()
                            datosRowModel.clear()
                            datosRowModel.addAll(rows)
                            adapter.notifyDataSetChanged()
                            binding.imagerror.visibility = View.GONE
                            binding.tablePrediccion.visibility = View.VISIBLE
                        } else {
                            Log.e("Try-Try-error","GettinImg")
                            showError()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("TryCatch-2","GettinImg")
                    activity?.runOnUiThread {
                        binding.imagerror.setImageResource(R.mipmap.nowifi)
                        Toast.makeText(requireContext(), "Error de conexion", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TryCatch","GettinImg")
            activity?.runOnUiThread {
                Toast.makeText(requireContext(), "Error de conexion", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Muestra un mensaje de error en caso de problemas al obtener los datos de la API
    private fun showError() {
        Toast.makeText(context, "Error al importar datos del servidor", Toast.LENGTH_SHORT).show()
    }
}
