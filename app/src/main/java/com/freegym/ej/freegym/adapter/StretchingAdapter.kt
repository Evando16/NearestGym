package com.freegym.ej.freegym.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freegym.ej.freegym.R
import com.freegym.ej.freegym.model.Stretching
import kotlinx.android.synthetic.main.simple_card.view.*

class StretchingAdapter(
        private val stretchingExercises: List<Stretching>,
        private val context: Context
) : RecyclerView.Adapter<StretchingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.simple_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stretchingExercises.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stretchingExercise = stretchingExercises[position]
        holder.bindView(stretchingExercise)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(stretchingExercise: Stretching) {
            val picture = itemView.simple_card__picture
            val title = itemView.simple_card__title

            title.text = stretchingExercise.name
            picture.setImageResource(R.drawable.st_alongamento_escalenos)
        }
    }
}