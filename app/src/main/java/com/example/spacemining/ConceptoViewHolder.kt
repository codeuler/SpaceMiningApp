package com.example.spacemining

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemining.model.ConceptoUiModel

/**
 * ViewHolder para los elementos del RecyclerView que muestran los conceptos.
 * @param containerView La vista raíz que contiene los elementos de la vista del ViewHolder.
 */
class ConceptoViewHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {
    private val conceptoTitulo: TextView by lazy { containerView.findViewById(R.id.dt) }
    private val conceptoDescripcion: TextView by lazy { containerView.findViewById(R.id.dd) }

    /**
     * Vincula los datos del concepto con la vista del ViewHolder.
     * @param conceptoData El objeto ConceptoUiModel que contiene los datos del concepto a mostrar.
     */
    fun bindData(conceptoData: ConceptoUiModel) {
        conceptoTitulo.text = conceptoData.titulo
        conceptoDescripcion.text = conceptoData.descripcion
    }
}
