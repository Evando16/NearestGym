package com.freegym.ej.freegym.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beust.klaxon.Klaxon
import com.freegym.ej.freegym.R
import com.freegym.ej.freegym.adapter.EquipmentsAdapter
import com.freegym.ej.freegym.model.EquipmentModel
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

    private fun buildEquipments(): List<EquipmentModel> {
        val equipmentsDbFileName = "equipments.db.json"
        var readDbFileContent = context?.assets?.open(equipmentsDbFileName)?.bufferedReader().use { it?.readText() }
        if (readDbFileContent == null)
            readDbFileContent = "[]"

        var equipmentsModel = Klaxon().parseArray<EquipmentModel>(readDbFileContent)
        if (equipmentsModel == null)
            equipmentsModel = emptyList()

        return equipmentsModel
    }
}