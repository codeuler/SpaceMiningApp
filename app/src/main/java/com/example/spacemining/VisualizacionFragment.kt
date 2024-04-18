package com.example.spacemining

import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import android.widget.ArrayAdapter;
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.spacemining.databinding.FragmentVisualizacionBinding

class VisualizacionFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private val matrix = Matrix()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentVisualizacionBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_visualizacion,container,false)

        imageView=binding.visualizacionImageView
        // Conectar el ScaleGestureDetector con el ImageView
        scaleGestureDetector = ScaleGestureDetector(this.requireContext(), ScaleListener())
        // Configurar el onTouchEvent para pasar eventos al detector de gestos de escala
        var lastTouchX = 0f
        var lastTouchY = 0f
        imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            if (event.pointerCount == 1) {
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        val deltaX = event.x - lastTouchX
                        val deltaY = event.y - lastTouchY
                        matrix.postTranslate(deltaX, deltaY)
                        imageView.imageMatrix = matrix
                    }
                }
                lastTouchX = event.x
                lastTouchY = event.y
            }
            true
        }


        val spinnerVisualizacion = binding.visualizacionSpinner
        val spinnerTipoGrafico = binding.tipoGraficoSpinner
        val consulta = mutableListOf("T","0","A")
        var listaVisualizacion = mutableListOf("")
        val itemsTipoGrafico = resources.getStringArray(R.array.tipos_graficos)
        val url = "https://space-mining-api.onrender.com/data/images/get?orbita=T&grafico=0&ejes=A"

        getImage(url,binding)

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
            getImage(direccion,binding)
        }

        return binding.root
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = scaleFactor.coerceIn(0.1f, 10.0f) // Limitar el zoom mínimo y máximo

            matrix.setScale(scaleFactor, scaleFactor)
            imageView.imageMatrix = matrix

            return true
        }
    }

    fun getImage(url: String, binding: FragmentVisualizacionBinding) {

        imageView.scaleType = ImageView.ScaleType.FIT_CENTER

        Glide.with(this.requireContext()).load(url).timeout(70000).thumbnail(Glide.with(
            this.requireContext()).load(R.drawable.imageloading)).error(R.mipmap.error
        ).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).listener(object :
            RequestListener<Drawable> {

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                imageView.scaleType = ImageView.ScaleType.MATRIX

                // Obtener las dimensiones de la imagen
                val imageWidth = resource?.intrinsicWidth ?: 0
                val imageHeight = resource?.intrinsicHeight ?: 0
                // Obtener las dimensiones del ImageView
                val imageViewWidth = imageView.width
                val imageViewHeight = imageView.height
                // Calcular la escala para que la imagen se ajuste al ImageView
                val scaleX = imageViewWidth.toFloat() / imageWidth.toFloat()
                val scaleY = imageViewHeight.toFloat() / imageHeight.toFloat()
                val scale = scaleX.coerceAtMost(scaleY) // Usar el menor de los dos para asegurar que la imagen se ajuste completamente
                // Aplicar la escala a la matriz
                matrix.setScale(scale, scale)
                imageView.imageMatrix = matrix
                // Calcular el desplazamiento para centrar la imagen si es necesario
                val translateX = (imageViewWidth - imageWidth * scale) / 2f
                val translateY = (imageViewHeight - imageHeight * scale) / 2f
                // Aplicar el desplazamiento a la matriz para centrar la imagen
                matrix.postTranslate(translateX, translateY)
                imageView.imageMatrix = matrix
                return false
            }
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                TODO("Not yet implemented")
            }
        }).into(
            binding.visualizacionImageView)
    }
}