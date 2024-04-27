package com.example.spacemining

import android.content.Context
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
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
    private lateinit var gestureDetector: GestureDetector
    private var scaleFactor = 1.0f
    private var min_scaleFactor = 1.0f
    private val max_scaleFactor = 5.0f
    private val matrix = Matrix()
    private var translateX = 0.0f
    private var translateY = 0.0f
    private lateinit var binding: FragmentVisualizacionBinding
    private val consulta = mutableListOf("T","0","A")
    private var listaVisualizacion = mutableListOf("")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_visualizacion,container,false)

        imageView=binding.visualizacionImageView
        // Conectar el GestureDetector con el ImageView
        gestureDetector = GestureDetector(requireContext(), GestureListener())
        // Conectar el ScaleGestureDetector con el ImageView
        scaleGestureDetector = ScaleGestureDetector(this.requireContext(), ScaleListener())
        // Configurar el onTouchEvent para pasar eventos al detector de gestos de escala
        var lastTouchX = 0f
        var lastTouchY = 0f
        imageView.setOnTouchListener { _, event ->
            if (event.pointerCount == 1) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        //Guardar la posición inicial del toque
                        lastTouchX = event.x
                        lastTouchY = event.y
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val deltaX = event.x - lastTouchX
                        val deltaY = event.y - lastTouchY
                        limitarDesplazamiento(deltaX,deltaY)
                        // Actualizar la posición del desplazamiento
                        lastTouchX = event.x
                        lastTouchY = event.y
                    }
                }
                gestureDetector.onTouchEvent(event)
            }else {
                scaleGestureDetector.onTouchEvent(event)
            }
            true
        }


        val spinnerVisualizacion = binding.visualizacionSpinner
        val spinnerTipoGrafico = binding.tipoGraficoSpinner


        val itemsTipoGrafico = resources.getStringArray(R.array.tipos_graficos)
        val url = "https://space-mining-api.onrender.com/data/images/get?orbita=T&grafico=0&ejes=A"

        if (isNetworkAvailable(requireContext())) {
            getImage(url,binding,imageView.width,imageView.height)
        } else {
            Glide.with(requireContext()).load(R.mipmap.nowifi).into(
                binding.visualizacionImageView
            )
        }

        binding.tituloText.text = "Grafico Dispersión (órbita) de Apogee - Period"

        spinnerTipoGrafico.adapter = crearArray(R.array.tipos_graficos)
        spinnerVisualizacion.adapter = crearArray(R.array.dispersion_orbita)

        spinnerTipoGrafico.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                consulta[2] = "A"
                when(itemsTipoGrafico[position]){
                    "Dispersión (no órbita)" -> {
                        setArraySpinner(R.array.dispersion_noorbita,"F","0")
                    }
                    "Dispersión (órbita)" -> {
                        setArraySpinner(R.array.dispersion_orbita,"T","0")
                    }
                    "Distribución (órbita)" -> {
                        setArraySpinner(R.array.distribucion_orbita,"T","1")
                    }
                    "Distribución (no órbita)" -> {
                        setArraySpinner(R.array.distribucion_noorbita,"F","1")
                    }
                    "Circular (órbita)" -> {
                        setArraySpinner(R.array.circular,"T","2")
                    }
                    "Circular (no órbita)" -> {
                        setArraySpinner(R.array.circular,"F","2")
                    }
                    "Histograma (no órbita)" -> {
                        setArraySpinner(R.array.histograma_noorbita,"F","3")
                    }
                    "Histograma (órbita)" -> {
                        setArraySpinner(R.array.histograma_orbita,"T","3")
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
            if (isNetworkAvailable(requireContext())) {
                getImage(direccion,binding,imageView.width,imageView.height)
            } else {
                Glide.with(requireContext()).load(R.mipmap.nowifi).into(
                    binding.visualizacionImageView
                )
            }
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
    private fun crearArray(textArrayResId: Int): ArrayAdapter<CharSequence> {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            textArrayResId,
            R.layout.spinner_visual
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spiner_item_visualizacion)
        }
        return adapter
    }

    private fun setArraySpinner(textArrayResId: Int,posicion0:String,posicion1:String){
        binding.visualizacionSpinner.adapter = crearArray(textArrayResId)
        consulta[0]=posicion0
        consulta[1]=posicion1
        listaVisualizacion = resources.getStringArray(textArrayResId).toMutableList()
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            reajustarImage()
            return true
        }
    }
    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val matrixValues = FloatArray(9)
            matrix.getValues(matrixValues)
            val currentX = matrixValues[Matrix.MTRANS_X]
            val currentY = matrixValues[Matrix.MTRANS_Y]

            val focusX = detector.focusX
            val focusY = detector.focusY

            scaleFactor *= detector.scaleFactor
            val newScaleFactor = scaleFactor.coerceIn(min_scaleFactor, max_scaleFactor)

            val offsetX = focusX - (focusX - currentX) * (newScaleFactor / scaleFactor)
            val offsetY = focusY - (focusY - currentY) * (newScaleFactor / scaleFactor)

            matrix.setScale(newScaleFactor, newScaleFactor)
            limitarDesplazamiento(offsetX,offsetY)

            imageView.imageMatrix = matrix
            return true
        }
    }

    fun limitarDesplazamiento(deltaX: Float, deltaY: Float) {
        val drawable = imageView.drawable
        val drawableWidth = drawable?.intrinsicWidth ?: 0
        val drawableHeight = drawable?.intrinsicHeight ?: 0
        val matrixValues = FloatArray(9)
        matrix.getValues(matrixValues)
        val currentX = matrixValues[Matrix.MTRANS_X]
        val currentY = matrixValues[Matrix.MTRANS_Y]
        val maxX = imageView.width - drawableWidth * matrixValues[Matrix.MSCALE_X]
        val maxY = imageView.height - drawableHeight * matrixValues[Matrix.MSCALE_Y]
        val currentXmod = currentX - maxX
        val currentYmod = currentY - maxY

        // Limitar el movimiento en X con y sin espacio libre
        val newX = when {
            // Movimiento a la derecha (deltaX positivo)
            deltaX > 0 && (currentXmod + deltaX <= 0) || (deltaX < 0 && currentX + deltaX >= 0) -> deltaX
            // Movimiento a la izquierda (deltaX negativo)
            deltaX > 0 && (currentX + deltaX <= 0) || (deltaX < 0 && currentXmod + deltaX >= 0) -> deltaX
            else -> 0f // No se realiza desplazamiento en X
        }

        // Limitar el movimiento en Y con y sin espacio libre
        val newY = when {
            // Movimiento hacia abajo (deltaY positivo)
            deltaY > 0 && (currentYmod + deltaY <= 0) || (deltaY < 0 && currentY + deltaY >= 0) -> deltaY
            // Movimiento hacia arriba (deltaY negativo)
            deltaY > 0 && (currentY + deltaY <= 0) || (deltaY < 0 && currentYmod + deltaY >= 0) -> deltaY
            else -> 0f // No se realiza desplazamiento en Y
        }

        // Aplicar el desplazamiento dentro de los límites
        matrix.postTranslate(newX, newY)
        imageView.imageMatrix = matrix
    }


    fun reajustarImage() {
        // Aplicar la escala a la matriz
        scaleFactor=min_scaleFactor
        matrix.setScale(scaleFactor, scaleFactor)
        // Aplicar el desplazamiento a la matriz para centrar la imagen
        matrix.postTranslate(translateX, translateY)
        imageView.imageMatrix = matrix
    }

    private fun getImage(url: String, binding: FragmentVisualizacionBinding, imageViewWidth: Int, imageViewHeight: Int) {

        imageView.scaleType = ImageView.ScaleType.FIT_CENTER

        Glide.with(this.requireContext())
            .load(url)
            .timeout(10000)
            .thumbnail(Glide.with(this.requireContext()).load(R.drawable.imageloading))
            .error(R.mipmap.error) // creo no se ejecuta adecuadamente, es decir, nunca se ejecuta | manejo el error en la funcion onLoadFailed
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .listener(object : RequestListener<Drawable> {

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                imageView.scaleType = ImageView.ScaleType.MATRIX
                val imageViewWidth = imageView.width
                val imageViewHeight = imageView.height
                // Obtener las dimensiones de la imagen
                val imageWidth = resource?.intrinsicWidth ?: 0
                val imageHeight = resource?.intrinsicHeight ?: 0
                Log.i("imageDimensions", "W: $imageWidth, H: $imageHeight VW: $imageViewWidth VH: $imageViewHeight")
                // Calcular la escala para que la imagen se ajuste al ImageView
                val scaleX = imageViewWidth.toFloat() / imageWidth.toFloat()
                val scaleY = imageViewHeight.toFloat() / imageHeight.toFloat()
                min_scaleFactor = scaleX.coerceAtMost(scaleY) // Usar el menor de los dos para asegurar que la imagen se ajuste completamente
                // Calcular el desplazamiento para centrar la imagen si es necesario
                translateX = (imageViewWidth - imageWidth * min_scaleFactor) / 2f
                translateY = (imageViewHeight - imageHeight * min_scaleFactor) / 2f
                reajustarImage()
                return false
            }
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.e("onLoadFailed","loading img failed")
                imageView.setImageResource(R.mipmap.error)
                return true
            }
        }).into(
            binding.visualizacionImageView)
    }
}