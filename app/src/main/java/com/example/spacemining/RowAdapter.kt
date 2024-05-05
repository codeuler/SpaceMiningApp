package com.example.spacemining

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemining.api.RowResponse

/**
 * Adaptador para el RecyclerView que muestra filas de datos.
 *
 * @param rows Lista de objetos RowResponse que se mostrarán en el RecyclerView.
 */
class RowAdapter(private val rows: List<RowResponse>) : RecyclerView.Adapter<RowHolder>() {

    /**
     * Crea y devuelve un nuevo ViewHolder para el RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RowHolder(layoutInflater.inflate(R.layout.item_row, parent, false))
    }

    /**
     * Devuelve el número total de elementos en el conjunto de datos.
     */
    override fun getItemCount(): Int = rows.size

    /**
     * Llama al método bind del ViewHolder para actualizar su contenido con el elemento en la posición dada.
     */
    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(rows[position])
    }
}
