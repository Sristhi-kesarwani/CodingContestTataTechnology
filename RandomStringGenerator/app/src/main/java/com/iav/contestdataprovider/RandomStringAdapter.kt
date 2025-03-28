package com.iav.contestdataprovider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RandomStringAdapter(private val items: List<RandomString>) : RecyclerView.Adapter<RandomStringAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(randomString: RandomString) {
            val stringText: TextView = itemView.findViewById(R.id.stringValue)
            val lengthText: TextView = itemView.findViewById(R.id.stringLength)
            val createdText: TextView = itemView.findViewById(R.id.stringCreated)
            val deleteBtn: Button = itemView.findViewById(R.id.deleteButton)

            stringText.text = randomString.value
            lengthText.text = "Length: ${randomString.length}"
            createdText.text = "Created: ${randomString.created}"


            deleteBtn.setOnClickListener {
                (items as MutableList).removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_random_string, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
