package com.austin.rooms2godemoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.austin.rooms2godemoapp.data.Email
import com.austin.rooms2godemoapp.databinding.FragmentEmailListBinding
import java.text.SimpleDateFormat
import java.util.*


class OrderRecyclerViewAdapter(
    private val values: List<Email>
) : RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentEmailListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.message
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
            .parse(item.date.replace("Z", "+0000"))

        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val formattedDate = date?.let { formatter.format(it) }
        holder.contentView.text = formattedDate
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentEmailListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}