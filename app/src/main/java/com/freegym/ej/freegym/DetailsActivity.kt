package com.freegym.ej.freegym

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object DetailsActivity {
        const val INTENT_TITLE = "INTENT_TITLE"
        const val INTENT_PARAGRAPHS = "INTENT_PARAGRAPHS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        applyTitle()
        buildContentParagraphs()
    }

    private fun applyTitle() {
        title = intent.getStringExtra(INTENT_TITLE)
    }

    private fun buildContentParagraphs() {
        val paragraphs = intent.getStringArrayListExtra(INTENT_PARAGRAPHS)
        paragraphs.forEach {
            val textView = TextView(this)

            textView.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
            textView.setPadding(25, 0 , 0, 0)

            main_layout.addView(textView)
        }
    }
}
