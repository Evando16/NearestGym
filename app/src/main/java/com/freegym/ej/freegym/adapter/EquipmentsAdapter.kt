package com.freegym.ej.freegym.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freegym.ej.freegym.DetailsActivity
import com.freegym.ej.freegym.R
import com.freegym.ej.freegym.model.Equipment
import kotlinx.android.synthetic.main.simple_card.view.*

class EquipmentsAdapter(
        private val equipments: List<Equipment>,
        private val context: Context
) : RecyclerView.Adapter<EquipmentsAdapter.ViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.simple_card, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return equipments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val equipment = equipments[position]
        holder.bindView(equipment)
    }

    override fun onClick(v: View?) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.INTENT_TITLE, "Multiexecitador")
        startActivity(context, intent, null)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(equipment: Equipment) {
            val card = itemView.simple_card
            val picture = itemView.simple_card__picture
            val title = itemView.simple_card__title

            card.setOnClickListener(this@EquipmentsAdapter)
            title.text = equipment.name
            picture.setImageResource(R.drawable.eq_caminhada_individual)
        }
    }


}