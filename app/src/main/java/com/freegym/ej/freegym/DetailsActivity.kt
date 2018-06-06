package com.freegym.ej.freegym

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_equipments.*

class DetailsActivity : AppCompatActivity() {

    companion object DetailsActivity {
        const val INTENT_TITLE = "INTENT_TITLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        /*
         * @TODO: get all needed values from Intent and inflate this layout.
         */
    }
}
