package com.example.spacemining

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemining.model.ConceptoUiModel

class ConceptoViewHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {
    private val conceptoTitulo: TextView
            by lazy { containerView.findViewById(R.id.dt)
            }
    private val conceptoDescripcion: TextView
            by lazy { containerView.findViewById(R.id.dd) }
    fun bindData(conceptoData: ConceptoUiModel) {
        conceptoTitulo.text = conceptoData.titulo
        conceptoDescripcion.text = conceptoData.descripcion
    }
}