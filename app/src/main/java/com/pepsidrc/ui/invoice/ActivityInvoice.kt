package com.pepsidrc.ui.invoice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.model.more.MoreItems
import com.pepsidrc.model.product.ItemMastersItem
import com.pepsidrc.ui.cart.CartDeatilActivity
import com.pepsidrc.ui.invoice.adapter.AdapteInvoice
import com.pepsidrc.ui.navigation.ui.more.adapter.AdapterMore
import com.pepsidrc.ui.productDetail.ProductDetailActivity
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.MiddleDividerItemDecoration
import kotlinx.android.synthetic.main.activity_invoice.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

class ActivityInvoice : AppCompatActivity(),
    AdapterViewClickListener<MoreItems> {
    override fun onClickAdapterView(objectAtPosition: MoreItems, viewType: Int, position: Int) {
    }

    private var adapterInvoice: AdapteInvoice? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)
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
        rv_invoice_items.layoutManager = manager
       /* rv_invoice_items.addItemDecoration(
            MiddleDividerItemDecoration(
                this,
                MiddleDividerItemDecoration.VERTICAL
            )
        )*/
        let {
            adapterInvoice = AdapteInvoice(this, it)

        }
        rv_invoice_items.adapter = adapterInvoice
        showData(getData())
    }
    private fun showData(data: MutableList<MoreItems>?) {
        adapterInvoice?.submitList(data)
        adapterInvoice?.notifyDataSetChanged()

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

            return Intent(context, ActivityInvoice::class.java)

        }
    }

    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.my_invoice)

    }

}
