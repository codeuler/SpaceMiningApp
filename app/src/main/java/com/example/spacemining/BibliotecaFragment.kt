package com.example.spacemining

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemining.databinding.FragmentBibliotecaBinding
import com.example.spacemining.databinding.FragmentStufInSpaceBinding
import com.example.spacemining.model.ConceptoUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//para la api
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class BibliotecaFragment : Fragment() {

    private lateinit var binding: FragmentBibliotecaBinding
    private val recyclerView: RecyclerView by lazy { binding.recyclerView }
    private val conceptoAdapter by lazy { conceptoAdapter(layoutInflater) }
    private val conceptosList = mutableListOf<ConceptoUiModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_biblioteca, container, false)

        recyclerView.adapter = conceptoAdapter
        // Inicializa la llamada a la API de manera asíncrona usando Coroutines
        lifecycleScope.launch {
            getDataFromApi(conceptoAdapter)
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
        }

        return binding.root
    }

    private suspend fun getDataFromApi(conceptoAdapter:conceptoAdapter) {
        try {
            // Crea una instancia de Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://space-mining-api.onrender.com")
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
                    Log.i("message", "Error: ${response.code()}")
                }
            }
        } catch (e: Exception) {
            Log.e("Error", "Error fetching data: ${e.message}")
        }
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