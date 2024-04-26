package com.example.spacemining

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemining.model.ConceptoUiModel

class ConceptoAdapter (
    private val layoutInflater: LayoutInflater
) :  RecyclerView.Adapter<ConceptoViewHolder>() {
    private val conceptoData = mutableListOf<ConceptoUiModel>()

    fun setData(conceptoData: List<ConceptoUiModel>) {
        this.conceptoData.clear()
        this.conceptoData.addAll(conceptoData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ConceptoViewHolder {
        val view = layoutInflater.inflate(R.layout.item_concepto,
            parent, false)
        return ConceptoViewHolder(view)
    }
    override fun getItemCount() = conceptoData.size
    override fun onBindViewHolder(holder: ConceptoViewHolder, position: Int) {
        holder.bindData(conceptoData[position])
    }
}