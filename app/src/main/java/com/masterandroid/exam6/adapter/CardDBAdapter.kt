package com.masterandroid.exam6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.masterandroid.exam6.R
import com.masterandroid.networkingroom.database.CardDB

class CardDBAdapter(var items:ArrayList<CardDB>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card,parent,false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val card = items[position]
        if (holder is CardViewHolder){
            holder.card_number.setText(card.card_number)
            holder.date.setText(card.date)
            holder.name.setText(card.name)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
    class CardViewHolder(view: View):RecyclerView.ViewHolder(view){
        val card_number:TextView = view.findViewById(R.id.tv_cardNumber)
        val date:TextView = view.findViewById(R.id.tv_date)
        val name:TextView = view.findViewById(R.id.tv_name)

    }
}