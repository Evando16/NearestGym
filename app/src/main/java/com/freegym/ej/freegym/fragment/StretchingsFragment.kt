package com.freegym.ej.freegym.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beust.klaxon.Klaxon
import com.freegym.ej.freegym.R
import com.freegym.ej.freegym.adapter.StretchingAdapter
import com.freegym.ej.freegym.model.StretchingModel
import kotlinx.android.synthetic.main.fragment_stretchings.*

class StretchingsFragment : Fragment() {
    companion object {
        fun newInstance(): EquipmentsFragment {
            return EquipmentsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stretchings, container, false)
    }

    override fun onStart() {
        super.onStart()
        loadStretchingCards()
    }

    private fun loadStretchingCards() {
        val stretchingListRecyclerView = stretching_recycler_view
        stretchingListRecyclerView.adapter = StretchingAdapter(buildStretching(), requireActivity())
        stretchingListRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun buildStretching(): List<StretchingModel> {
        val equipmentsDbFileName = "stretching.db.json"
        var readDbFileContent = context?.assets?.open(equipmentsDbFileName)?.bufferedReader().use { it?.readText() }
        if (readDbFileContent == null)
            readDbFileContent = "[]"

        var stretchingModel = Klaxon().parseArray<StretchingModel>(readDbFileContent)
        if (stretchingModel == null)
            stretchingModel = emptyList()

        return stretchingModel
    }
}