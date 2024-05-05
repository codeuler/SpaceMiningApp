package com.example.spacemining

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemining.model.ConceptoUiModel

/**
 * Adaptador para el RecyclerView que muestra la lista de conceptos.
 * @property layoutInflater El LayoutInflater utilizado para inflar la vista de cada elemento del RecyclerView.
 */
class ConceptoAdapter(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<ConceptoViewHolder>() {
    private val conceptoData = mutableListOf<ConceptoUiModel>()

    /**
     * Actualiza los datos del adaptador con una nueva lista de conceptos.
     * @param conceptoData La nueva lista de conceptos a mostrar.
     */
    fun setData(conceptoData: List<ConceptoUiModel>) {
        this.conceptoData.clear()
        this.conceptoData.addAll(conceptoData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConceptoViewHolder {
        val view = layoutInflater.inflate(R.layout.item_concepto, parent, false)
        return ConceptoViewHolder(view)
    }

    override fun getItemCount() = conceptoData.size

    override fun onBindViewHolder(holder: ConceptoViewHolder, position: Int) {
        holder.bindData(conceptoData[position])
    }
}
