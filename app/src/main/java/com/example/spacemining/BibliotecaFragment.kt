package com.example.spacemining

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacemining.databinding.FragmentBibliotecaBinding
import com.example.spacemining.model.ConceptoUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
//para la api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


class BibliotecaFragment : Fragment() {

    private lateinit var binding: FragmentBibliotecaBinding
    private val recyclerView: RecyclerView by lazy { binding.recyclerView }
    private val conceptoAdapter by lazy { ConceptoAdapter(layoutInflater) }
    private val conceptosList = mutableListOf<ConceptoUiModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_biblioteca, container, false)

        Glide.with(this.requireContext()).load(R.drawable.imageloading).into(
            binding.imageCargaConceptos)

        recyclerView.adapter = conceptoAdapter
        // Inicializa la llamada a la API de manera asíncrona usando Coroutines
        lifecycleScope.launch {
            if (isNetworkAvailable(requireContext())) {
                getDataFromApi(conceptoAdapter)
            } else {
                Glide.with(requireContext()).load(R.mipmap.nowifi).into(
                    binding.imageCargaConceptos
                )
            }
        }
        // Función para actualizar el RecyclerView con la lista filtrada
        fun updateRecyclerView(filteredList: List<ConceptoUiModel>) {
            // Actualiza el adaptador del RecyclerView con la lista filtrada
            conceptoAdapter.setData(filteredList)
        }
        // Escucha cambios en el texto del EditText para realizar la búsqueda
        binding.imageView2.setOnClickListener {
            val searchText = binding.editTextText.text.toString().trim().lowercase()

            // Filtra los elementos de conceptosList según el texto de búsqueda en el título o la descripción
            val filteredList = conceptosList.filter { concepto ->
                concepto.titulo.lowercase().contains(searchText) ||
                        concepto.descripcion.lowercase().contains(searchText)
            }
            // Actualiza el RecyclerView con la lista filtrada
            updateRecyclerView(filteredList)
            hideKeyBoard(it)
        }

        return binding.root
    }

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

    private fun hideKeyBoard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }

    private suspend fun getDataFromApi(conceptoAdapter:ConceptoAdapter) {
        try {
            val client = OkHttpClient.Builder()
                .connectTimeout(70, TimeUnit.SECONDS)
                .readTimeout(70, TimeUnit.SECONDS)
                .build()
            // Crea una instancia de Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://space-mining-api.onrender.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Crea una instancia de la interfaz ApiService
            val apiService = retrofit.create(ApiService::class.java)

            // Realiza la llamada a la API de manera asíncrona
            val response = apiService.getSpaceTrackData()

            // Maneja la respuesta en el hilo principal usando withContext
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val spaceTrackResponse = response.body()
                    spaceTrackResponse?.data?.let { data->
                        val descriptionBuilder = StringBuilder()
                        data.forEach { (concept, descriptions) ->
                            descriptions.forEach { description ->
                                descriptionBuilder.append(description).append("\n")
                            }
                            val conceptoUiModel = ConceptoUiModel(concept, descriptionBuilder.toString())
                            conceptosList.add(conceptoUiModel)
                            descriptionBuilder.clear()
                        }
                    }
                    conceptoAdapter.setData(conceptosList)
                } else {
                    Log.e("message", "Error: ${response.code()}")
                    conceptosList.add(ConceptoUiModel("Conceptos", "No encontramos conceptos disponibles intentalo mas tarde porfavor ;D"))
                    conceptoAdapter.setData(conceptosList)
                }
            }
        } catch (e: Exception) {
            Log.e("Error", "Error fetching data: ${e.message}")
            conceptosList.add(ConceptoUiModel("Fallo de conexion", "Tuvimos problemas para conectarnos con el servidor, asegurate de estar conectado a internet. ;D"))
            conceptoAdapter.setData(conceptosList)
        }
        binding.imageCargaConceptos.visibility=View.GONE
    }
}

// Define la interfaz para la llamada a la API
interface ApiService {
    @GET("/data/conceptos/space_track")
    suspend fun getSpaceTrackData(): retrofit2.Response<SpaceTrackResponse>
}
class SpaceTrackResponse(
    val data: Map<String, List<String>>, // La respuesta contiene un mapa de conceptos a listas de descripciones
    val time: String,
    val type: String
)