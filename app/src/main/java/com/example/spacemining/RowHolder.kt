package com.example.spacemining

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemining.api.RowResponse
import com.example.spacemining.databinding.ItemRowBinding

/**
 * ViewHolder para el RecyclerView que muestra filas de datos.
 *
 * @param view Vista raíz de la fila del RecyclerView.
 */
class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Enlace de datos con la vista de la fila
    private val binding = ItemRowBinding.bind(view)

    /**
     * Método para vincular datos a la vista de la fila.
     *
     * @param row Objeto RowResponse que contiene los datos a mostrar en la fila.
     */
    fun bind(row: RowResponse) {
        binding.apply {
            textViewobjectId.text = row.objectId
            textViewapogee.text = row.apogee.toString()
            textViewperigee.text = row.perigee.toString()
            textViewperiod.text = row.period.toString()
        }
    }
}
