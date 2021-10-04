package com.pepsidrc.ui.mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pepsidrc.R
import com.pepsidrc.ui.place_order.PlaceOrderActivity
import com.pepsidrc.utils.AndroidUtils
import kotlinx.android.synthetic.main.app_custom_tool_bar_filter.*

class MobileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile)
        btn_clear.setOnClickListener {

            /*startActivity(
                PlaceOrderActivity.getIntent(this)
            )*/
        }
    }

    public override fun onResume() {
        super.onResume()
        btn_clear.visibility= View.VISIBLE

        btn_clear.text = AndroidUtils.getString(R.string.next)
        tv_tool_title.text=AndroidUtils.getString(R.string.step2)

    }

    companion object {
        const val KEY_ORDER_ID = "KEY_ORDER_ID"

        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, MobileActivity::class.java)

        }
    }

}
