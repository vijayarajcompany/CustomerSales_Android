package com.pepsidrc.ui.coupons

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.model.cart.cartItems.OrderItemsItem
import com.pepsidrc.model.coupons.PromotionsItem
import com.pepsidrc.ui.cart.CartDeatilActivity
import com.pepsidrc.ui.cart.CartViewModel
import com.pepsidrc.ui.cart.adapter.AdapterCartListProducts
import com.pepsidrc.ui.coupons.adapter.AdapterCouponList
import com.pepsidrc.ui.productDetail.ProductDetailActivity
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_cart_deatil.*
import kotlinx.android.synthetic.main.activity_coupon.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

class CouponActivity : BaseActivity<CouponViewModel>(CouponViewModel::class),
    AdapterViewClickListener<PromotionsItem> {
    override fun onClickAdapterView(
        objectAtPosition: PromotionsItem,
        viewType: Int,
        position: Int
    ) {

        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_APPLY_COUPON -> {

                let {

                    if (NetworkUtil.isInternetAvailable(this)) {
                        model.applyCoupon(couponList?.get(position)?.promoNo, order_ID)
                    }
                }
            }
        }
    }

    override fun layout(): Int = R.layout.activity_coupon

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var adapterCouponList: AdapterCouponList? = null
    private var couponList: ArrayList<PromotionsItem>? = ArrayList()
    var order_ID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        var manager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        intent?.run {

            order_ID = getIntExtra(KEY_ORDER_ID, 0)
        }
        rv_coupon_items?.layoutManager = manager
        let {
            adapterCouponList = AdapterCouponList(this, this)
            rv_coupon_items.adapter = adapterCouponList
        }
        fl_left_img_container.setOnClickListener {

            onBackPressed()
        }
        barIcons.visibility = View.GONE

        subscribeUi()
        subscribeLoading()
        getData()
    }

    private fun subscribeUi() {
        model.couponListViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            it?.data?.promotions.let {

                if (it != null && it?.size > 0) {
                    couponList = it
                    adapterCouponList?.submitList(it)
                    adapterCouponList?.notifyDataSetChanged()
                }
            }

        })
        model.applyCouponViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            if (it.success) {
                showSnackbar(it?.message?.message, true)

                val handler = Handler()
                handler.postDelayed({
                    onBackPressed()
                }, 2000)
            } else {
                showSnackbar(
                    it?.errors?.get(0)?.fieldLabel + " " + it?.errors?.get(0)?.detail,
                    false
                )

            }
        })


    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.let {
                UiUtils.showInternetDialog(this, R.string.something_went_wrong)
            }
        })
    }

    private fun getData() {
        if (NetworkUtil.isInternetAvailable(this)) {
            model.getCouponList()
        }
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    companion object {
        const val KEY_ORDER_ID = "KEY_ORDER_ID"

        fun getIntent(context: Context?, orderId: Int): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, CouponActivity::class.java).putExtra(KEY_ORDER_ID, orderId)

        }
    }

}
