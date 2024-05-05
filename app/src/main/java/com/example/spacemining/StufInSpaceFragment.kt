package com.example.spacemining

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.spacemining.databinding.FragmentStufInSpaceBinding

/**
 * Fragmento que muestra información sobre objetos en el espacio.
 */
class StufInSpaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el diseño para este fragmento utilizando DataBinding
        val binding: FragmentStufInSpaceBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_stuf_in_space, container, false
        )

        // Obtiene la URL del enlace
        val url = "https://${binding.linkStuff.text.toString()}"

        // Habilita el soporte para JavaScript en el WebView
        binding.webView.settings.javaScriptEnabled = true
        // Configura para cargar la página en modo de escritorio
        binding.webView.settings.userAgentString =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"

        // Carga la URL en el WebView
        binding.webView.loadUrl(url)

        // Configura el clic del enlace para abrir en el navegador externo
        binding.linkStuff.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        return binding.root
    }

}
