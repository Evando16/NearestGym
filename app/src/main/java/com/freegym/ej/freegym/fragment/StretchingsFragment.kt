package com.freegym.ej.freegym.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freegym.ej.freegym.R

class StretchingsFragment : Fragment() {
    companion object {
        fun newInstance(): EquipmentsFragment {
            return EquipmentsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stretchings, container, false)
    }
}