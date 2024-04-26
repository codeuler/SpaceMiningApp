package com.example.spacemining

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemining.api.RowResponse

class RowAdapter (private val rows:List<RowResponse>):RecyclerView.Adapter<RowHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RowHolder(layoutInflater.inflate(R.layout.item_row,parent,false))
    }

    override fun getItemCount(): Int = rows.size

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(rows[position])
    }
}