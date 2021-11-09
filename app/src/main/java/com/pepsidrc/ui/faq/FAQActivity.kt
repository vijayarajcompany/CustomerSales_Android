package com.pepsidrc.ui.faq

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.pepsidrc.R
import com.pepsidrc.utils.AndroidUtils
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

class FAQActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        val adjustEvent = AdjustEvent("nha3ey")
        Adjust.trackEvent(adjustEvent)
        fl_left_img_container.setOnClickListener {

            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.faq)
        barIcons.visibility = View.GONE

        val MorePg_FAQ_link_event = AdjustEvent("nha3ey")
        MorePg_FAQ_link_event.setCallbackId("MorePg_FAQ_link_event");
        Adjust.trackEvent(MorePg_FAQ_link_event)

    }

    companion object {
        const val KEY_PRODUCT_DATA = "KEY_SUBCATEGORY_DATA"
        const val KEY_TITLE = "KEY_TITLE"

        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, FAQActivity::class.java)

        }
    }

}
