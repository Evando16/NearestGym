package com.freegym.ej.freegym.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freegym.ej.freegym.DetailsActivity
import com.freegym.ej.freegym.R
import com.freegym.ej.freegym.model.StretchingModel
import kotlinx.android.synthetic.main.simple_card.view.*

class StretchingAdapter(
        private val stretchingModelExercises: List<StretchingModel>,
        private val context: Context
) : RecyclerView.Adapter<StretchingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.simple_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stretchingModelExercises.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stretchingExercise = stretchingModelExercises[position]
        holder.bindView(stretchingExercise)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(stretchingModelExercise: StretchingModel) {
            val card = itemView.simple_card
            val picture = itemView.simple_card__picture
            val title = itemView.simple_card__title

            card.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra(DetailsActivity.INTENT_TITLE, stretchingModelExercises[adapterPosition].name)
                    intent.putStringArrayListExtra(DetailsActivity.INTENT_PARAGRAPHS, stretchingModelExercises[adapterPosition].description)
                    ContextCompat.startActivity(context, intent, null)
                }
            })

            title.text = stretchingModelExercise.name
            picture.setImageResource(R.drawable.eq_caminhada_individual)
        }
    }
}