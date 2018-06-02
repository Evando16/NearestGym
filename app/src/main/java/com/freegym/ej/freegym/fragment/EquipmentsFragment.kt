package com.freegym.ej.freegym.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freegym.ej.freegym.R
import com.freegym.ej.freegym.adapter.EquipmentsAdapter
import com.freegym.ej.freegym.model.Equipment
import kotlinx.android.synthetic.main.fragment_equipments.*

class EquipmentsFragment : Fragment() {

    companion object {
        fun newInstance(): EquipmentsFragment {
            return EquipmentsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_equipments, container, false)
    }

    override fun onStart() {
        super.onStart()
        loadEquipmentsCards()
    }

    private fun loadEquipmentsCards() {
        val equipmentsListRecyclerView = equipments_recycler_view
        equipmentsListRecyclerView.adapter = EquipmentsAdapter(buildEquipments(), requireActivity())
        equipmentsListRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun buildEquipments(): List<Equipment> {
        return listOf(
                Equipment("Multiexercitador", ""),
                Equipment("Pressão de Pernas", ""),
                Equipment("Remada Sentada", ""),
                Equipment("Extensora e Agachamento", ""),
                Equipment("Alongador", ""),
                Equipment("Rotação Vertical", ""),
                Equipment("Simulador de Cavalgada", ""),
                Equipment("Rotação Dupla Digonal", ""),
                Equipment("Simulador de Caminhada", ""),
                Equipment("Esqui", "")
        )
    }
}