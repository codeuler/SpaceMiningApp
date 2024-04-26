package com.example.spacemining

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemining.api.RowResponse
import com.example.spacemining.databinding.ItemRowBinding

class RowHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemRowBinding.bind(view)
        fun bind(rows: RowResponse){
            binding.textViewobjectId.text = rows.objectId
            binding.textViewapogee.text = rows.apogee.toString()
            binding.textViewperigee.text = rows.perigee.toString()
            binding.textViewperiod.text = rows.period.toString()
        }
}