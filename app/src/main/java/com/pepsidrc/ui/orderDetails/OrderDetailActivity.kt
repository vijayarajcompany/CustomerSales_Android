package com.pepsidrc.ui.orderDetails

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.orderlist.OrdersItem
import com.pepsidrc.ui.cart.CartDeatilActivity
import com.pepsidrc.ui.navigation.ui.orders.adapter.AdapterOrderDetailsList
import com.pepsidrc.ui.search.SearchActivity
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.layout_order_status.*

class OrderDetailActivity : BaseActivity<OrderDetailsViewModel>(OrderDetailsViewModel::class) {
    override fun layout(): Int = R.layout.activity_order_detail
    var preferenceManager : PreferenceManager?=null


    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {

        const val KEY_ORDER_DATA = "KEY_ORDER_DATA"

        fun getIntent(context: Context?, ordersItem: OrdersItem): Intent? {
            val intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra(KEY_ORDER_DATA, ordersItem)
            return intent
        }
    }


    lateinit var ordersItem: OrdersItem
    private var adapter: AdapterOrderDetailsList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager(this)

        fl_left_img_container.setOnClickListener {

            onBackPressed()
        }
        iv_search.setOnClickListener {

            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                SearchActivity.getIntent(this),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )

        }
        intent?.run {
            ordersItem = this!!.getParcelableExtra<OrdersItem>(KEY_ORDER_DATA)!!
            text_order_id_value?.text = ordersItem.orderNumber
            text_order_status_value?.text = ordersItem.status

            txt_grand_total.text = AndroidUtils.getString(R.string.price_type) +
                    " " + ordersItem?.totalAmount?.toString()
            txt_total_items.text = ordersItem?.orderItems?.size.toString()
            txt_charges.text=ordersItem?.sourceAmount?.toString()

         /*   ordersItem?.totalAmount?.let {
                ordersItem?.sourceAmount?.let { it1 ->

                    var am =
                        it.toDouble() - it1.toDouble()
                    txt_promotion.text = am.toString()

                }
            }*/
            /*   if(ordersItem?.promotionAmount!=null) {
                   txt_promotion.text = "(-)" + ordersItem?.promotionAmount
               }*/
            text_order_total_price.text=AndroidUtils.getString(R.string.price_type) +
                    " " + ordersItem?.totalAmount?.toString()

            text_order_date_value?.text = ordersItem?.orderDate
            //AndroidUtils.getDateOrder(ordersItem?.orderDate)
            text_order_payment_value?.text = ordersItem.order_payment_type
         //   text_shipping_name.text=ordersItem.addresses[0].get
            var manager = LinearLayoutManager(
                this@OrderDetailActivity,
                LinearLayoutManager.VERTICAL, false
            )
            adapter = AdapterOrderDetailsList(this@OrderDetailActivity)
            rv_order_item?.layoutManager = manager
            rv_order_item?.adapter = adapter
            adapter?.submitList(ordersItem?.orderItems)
            adapter?.notifyDataSetChanged()

            text_shipping_name.text=ordersItem?.address?.title
            text_shipping_address.text=ordersItem?.address?.address
            text_shipping_add_number.text=ordersItem?.address?.mobileNumber

        }
        btnCancelOrder.setOnClickListener {

            if (NetworkUtil.isInternetAvailable(this)) {
                model.cancelOrder(ordersItem?.id)
            }
        }
        iv_cart.setOnClickListener {
            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                CartDeatilActivity.getIntent(this)
            )
        }
        subscribeLoading()
        subscribeUi()
        model.orderDetails(ordersItem?.id)
    }

    private fun subscribeUi() {
        model.cancelOrderModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
                if (it.success) {
                    showSnackbar(it?.message, true)

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
        model.orderDetailsModel.observe(this, Observer {
            try {
                Logger.Debug("DEBUG", it.toString())
                if (it.success) {
                    rlDeliveryStatus.visibility = View.VISIBLE
                    if (it?.data?.order?.status.equals("placed")) {
                        iv_order_confirmed.setImageResource(R.drawable.status_green)
                        llConfirmed.setBackgroundResource(R.drawable.rectangle)
                    } else if (it?.data?.order?.status.equals("shipped")) {
                        iv_order_on_the_way.setImageResource(R.drawable.status_green)
                        llOnTheWay.setBackgroundResource(R.drawable.rectangle)
                        iv_order_confirmed.setImageResource(R.drawable.tick_grey)
                        iv_DeliveryCreated.setImageResource(R.drawable.tick_grey)


                        llFrom.visibility = View.VISIBLE
                        llTO.visibility = View.VISIBLE

                        tvFrom.setText("PepsiDRC")
                        tvText1.setText(it?.data?.order?.user?.firstName)
                        if (it?.data?.order?.address?.title != null && !it?.data?.order?.address?.title.equals(
                                ""
                            )
                        ) {
                            tvText2.setText(it?.data?.order?.address?.title + ", " + it?.data?.order?.address?.address)
                        } else {
                            tvText2.setText(it?.data?.order?.address?.address)
                        }
                    } else if (it?.data?.order?.status.equals("complete")) {
                        iv_order_delivered.setImageResource(R.drawable.status_green)
                        llDelivered.setBackgroundResource(R.drawable.rectangle)
                        iv_order_confirmed.setImageResource(R.drawable.tick_grey)
                        iv_DeliveryCreated.setImageResource(R.drawable.tick_grey)
                        iv_order_on_the_way.setImageResource(R.drawable.tick_grey)

                    } else if (it?.data?.order?.status.equals("cancelled")) {
                        iv_order_delivered.setImageResource(R.drawable.status_green)
                        llDelivered.setBackgroundResource(R.drawable.rectangle)
                        tvDelivered.setText("Cancelled")
                        iv_order_confirmed.setImageResource(R.drawable.tick_grey)
                        iv_DeliveryCreated.setImageResource(R.drawable.tick_grey)
                        iv_order_on_the_way.setImageResource(R.drawable.tick_grey)
                    } else if (it?.data?.order?.status.equals("processing")) {
                        iv_DeliveryCreated.setImageResource(R.drawable.status_green)
                        llDeliveryCreated.setBackgroundResource(R.drawable.rectangle)
                        iv_order_confirmed.setImageResource(R.drawable.tick_grey)

                    } else {
                        btnCancelOrder.visibility = View.INVISIBLE
                    }
                } else {
                    showSnackbar(
                        it?.errors?.get(0)?.fieldLabel + " " + it?.errors?.get(0)?.detail,
                        false
                    )

                }
            } catch (e: Exception) {
                e.printStackTrace()
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
    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.order_details)
        if(preferenceManager?.getBooleanPreference( Config.SharedPreferences.PROPERTY_IS_CART)!!){
            ivCounter.visibility=View.VISIBLE
            ivCounter.setText(preferenceManager?.getStringPreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE,""))

        }
        else{
            ivCounter.visibility=View.INVISIBLE

        }

    }
}
