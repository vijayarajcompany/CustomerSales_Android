package com.pepsidrc.ui.credits

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.more.MoreItems
import com.pepsidrc.ui.cart.CartDeatilActivity
import com.pepsidrc.ui.credits.adapter.AdapteCredits
import com.pepsidrc.ui.invoice.ActivityInvoice
import com.pepsidrc.ui.invoice.adapter.AdapteInvoice
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.MiddleDividerItemDecoration
import kotlinx.android.synthetic.main.activity_credits.*
import kotlinx.android.synthetic.main.activity_invoice.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

class ActivityCredits : AppCompatActivity(),
    AdapterViewClickListener<MoreItems> {
    override fun onClickAdapterView(objectAtPosition: MoreItems, viewType: Int, position: Int) {
    }
    private var adapteCredits: AdapteCredits? = null
    var preferenceManager: PreferenceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)
        preferenceManager = PreferenceManager(this)

        fl_left_img_container.setOnClickListener {

            onBackPressed()
        }

        iv_cart.setOnClickListener {
            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                CartDeatilActivity.getIntent(this)
            )
        }

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_credits_items.layoutManager = manager
       /* rv_credits_items.addItemDecoration(
            MiddleDividerItemDecoration(
                this,
                MiddleDividerItemDecoration.VERTICAL
            )
        )*/
        let {
            adapteCredits = AdapteCredits(this, it)

        }
        rv_credits_items.adapter = adapteCredits
        showData(getData())
    }
    private fun showData(data: MutableList<MoreItems>?) {
        adapteCredits?.submitList(data)
        adapteCredits?.notifyDataSetChanged()

    }

    private fun getData(): MutableList<MoreItems> {

        val moreItems: MutableList<MoreItems> = ArrayList()
        moreItems!!.add(
            MoreItems(
                R.drawable.ic_profile,
                AndroidUtils.getString(R.string.my_profile)
            )
        )
        moreItems!!.add(
            MoreItems(
                R.drawable.ic_invoice,
                AndroidUtils.getString(R.string.my_invoice)
            )
        )
        moreItems!!.add(
            MoreItems(
                R.drawable.ic_credits,
                AndroidUtils.getString(R.string.my_credits)
            )
        )
        moreItems!!.add(MoreItems(R.drawable.ic_credits, AndroidUtils.getString(R.string.logout)))
        moreItems!!.add(
            MoreItems(
                R.drawable.ic_credits,
                AndroidUtils.getString(R.string.my_credits)
            )
        )
        moreItems!!.add(
            MoreItems(
                R.drawable.ic_credits,
                AndroidUtils.getString(R.string.my_credits)
            )
        )
        return moreItems
    }
    companion object {
        const val KEY_PRODUCT_DATA = "KEY_SUBCATEGORY_DATA"
        const val KEY_TITLE = "KEY_TITLE"

        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, ActivityCredits::class.java)

        }
    }


    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.my_credits)
        if (preferenceManager?.getBooleanPreference(Config.SharedPreferences.PROPERTY_IS_CART)!!) {
            ivCounter.visibility = View.VISIBLE
            ivCounter.setText(preferenceManager?.getStringPreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE,""))


        } else {
            ivCounter.visibility = View.INVISIBLE

        }

    }
}
