package com.freegym.ej.freegym

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.util.Half.toFloat
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object DetailsActivity {
        const val INTENT_TITLE = "INTENT_TITLE"
        const val INTENT_BANNERS = "INTENT_BANNERS"
        const val INTENT_PARAGRAPHS = "INTENT_PARAGRAPHS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        applyTitle()
        buildBannerPictures()
        buildContentParagraphs()
    }

    private fun applyTitle() {
        title = intent.getStringExtra(INTENT_TITLE)
    }

    private fun buildBannerPictures() {
        val banners = intent.getStringArrayListExtra(INTENT_BANNERS)
        banners.forEach({
            val imageView = ImageView(this)
            Glide.with(this).load(it).into(imageView)
            banners_layout.addView(imageView)
        })
    }

    private fun buildContentParagraphs() {
        val paragraphs = intent.getStringArrayListExtra(INTENT_PARAGRAPHS)
        paragraphs.forEach {
            val textView = TextView(this)

            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(0, 0, 0, 10)

            textView.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
            textView.layoutParams = layoutParams

            description.addView(textView)
        }
    }
}
