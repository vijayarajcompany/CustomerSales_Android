package com.pepsidrc.ui.cart

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.base.StoreProducts
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.PaymentMode
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.UpdateOrder
import com.pepsidrc.model.cart.UpdateOrderItemsAttributesItem
import com.pepsidrc.model.cart.UpdateProductRequestPayload
import com.pepsidrc.model.cart.cartItems.CartListResponsePayload
import com.pepsidrc.model.cart.cartItems.Order
import com.pepsidrc.model.cart.cartItems.OrderItemsItem
import com.pepsidrc.model.place_order.OrderPlaceResponsePayload
import com.pepsidrc.model.shipping.ShippingData
import com.pepsidrc.model.shipping.ShippingResponsePayload
import com.pepsidrc.ui.adress.AddressActivity
import com.pepsidrc.ui.cart.adapter.AdapterCartListProducts
import com.pepsidrc.ui.cart.adapter.AdapterPaymentMode
import com.pepsidrc.ui.cart.dialog.PaymentModeDialog
import com.pepsidrc.ui.cart.dialog.PaymentModeDialogListener
import com.pepsidrc.ui.coupons.CouponActivity
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_cart_deatil.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.dialog_custom.view.*

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent


class CartDeatilActivity : BaseActivity<CartViewModel>(CartViewModel::class),
    AdapterViewClickListener<OrderItemsItem>, AdapterPaymentMode.RecyclerViewItemClickListener,
    PaymentModeDialogListener {

    override fun clickOnItem(data: PaymentMode) {
        mode = data.value
    }

    override fun placeOrder() {
        if (mode == null) {
            showSnackbar(
                AndroidUtils.getString(R.string.please_select_mode),
                false
            )
        } else {
            model.placeOrder(mode)
        }
    }

    override fun onClickAdapterView(
        objectAtPosition: OrderItemsItem,
        viewType: Int,
        position: Int
    ) {
        orderItemsItem = objectAtPosition
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_PLUS_PRODUCT -> {

                let {
                    isDelete = false

                    val CartPg_Add_Qty_event = AdjustEvent("66ltl8")
                    CartPg_Add_Qty_event.setCallbackId("CartPg_Add_Qty_event");
                    Adjust.trackEvent(CartPg_Add_Qty_event)

                    addProductToCart(objectAtPosition, objectAtPosition.quantity + 1, 0)
                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_MINUS_PRODUCT -> {

                val CartPg_Remove_Qty_event = AdjustEvent("ck0r1o")
                CartPg_Remove_Qty_event.setCallbackId("CartPg_Remove_Qty_event");
                Adjust.trackEvent(CartPg_Remove_Qty_event)

                let {
                    UiUtils.hideSoftKeyboard(it)
                    if (objectAtPosition.quantity > 1) {
                        isDelete = false

                        addProductToCart(objectAtPosition, objectAtPosition.quantity - 1, 0)
                    } else {
                        isDelete = true
                        addProductToCart(objectAtPosition, objectAtPosition.quantity - 1, 1)
                    }
                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_QUANTITY_CHANGED -> {

                let {
                    UiUtils.hideSoftKeyboard(it)

                    if (objectAtPosition.quantity >= 1) {
                        isDelete = false

                        addProductToCart(objectAtPosition, objectAtPosition.quantity, 0)

                    } else {
                        isDelete = true

                        addProductToCart(objectAtPosition, objectAtPosition.quantity, 1)
                    }
                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_DELETE_PRODUCT -> {

                let {
                    UiUtils.hideSoftKeyboard(it)
                    isDelete = true

                    val CartPg_Delete_Product_event = AdjustEvent("939v0d")
                    CartPg_Delete_Product_event.setCallbackId("CartPg_Delete_Product_event");
                    Adjust.trackEvent(CartPg_Delete_Product_event)

                    addProductToCart(objectAtPosition, objectAtPosition.quantity - 1, 1)

                }
            }
        }
    }


    companion object {
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, CartDeatilActivity::class.java)

        }
    }

    override fun layout(): Int = R.layout.activity_cart_deatil


    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var adapterCartListProducts: AdapterCartListProducts? = null
    private var orderList: ArrayList<OrderItemsItem>? = ArrayList()
    internal var paymentModeDialog: PaymentModeDialog? = null
    internal var mode: String? = null
    internal var isDelete: Boolean = false
    private var orderItemsItem: OrderItemsItem? = null
    private var order: Order? = null
    private var shippingData: ShippingData? = null

    var preferenceManager: PreferenceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager(this)

        var manager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        rv_cart_items?.layoutManager = manager
        let {
            adapterCartListProducts = AdapterCartListProducts(this, this)
            rv_cart_items.adapter = adapterCartListProducts
        }
        rlBuyNow.setOnClickListener {
            // getModes()
            val ShopPg_BuyNow_event = AdjustEvent("qc39zd")
            ShopPg_BuyNow_event.setCallbackId("ShopPg_BuyNow_event");
            Adjust.trackEvent(ShopPg_BuyNow_event)

            order?.totalAmount?.let {

                if(it.toDouble()>=100.0){
                    startActivity(
                        AddressActivity.getIntent(this,txt_grand_total_value.text.toString())
                    )
                }else{
                  amountCheck()
                }


            }
        }
        fl_left_img_container.setOnClickListener {

            onBackPressed()
        }
        rlShopMore.setOnClickListener {
            val ShopMore_event = AdjustEvent("22v98a")
            ShopMore_event.setCallbackId("ShopMore_event");
            Adjust.trackEvent(ShopMore_event)

            startActivity(LandingNavigationActivity.getIntent(this, 2))

        }
        tvCouponList.setOnClickListener {

            startActivity(
                CouponActivity.getIntent(this, order?.id!!)
            )
        }
        barIcons.visibility = View.GONE
        subscribeUi()
        subscribeLoading()
    }

    private fun amountCheck() {
        this?.let {
            val dialogBuilder = android.app.AlertDialog.Builder(it)

            // set message of alert dialog
            dialogBuilder.setMessage("Total amount must be AED 100 or more to place order")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->

                   dialog.dismiss()

                })
                // negative button text and action

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle(AndroidUtils.getString(R.string.app_name))
            // show alert dialog
            alert.show()
        }
    }

    override fun onResume() {
        super.onResume()
        getData()

    }

    private fun getData() {
        if (NetworkUtil.isInternetAvailable(this)) {
            model.getCartList()
          //  model.getShippingCharge()
        }
    }

    private fun subscribeUi() {
        model.cartViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })
        model.addProductModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })
        model.placeOrderModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })
        model.shippingModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
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

    private fun showData(data: CartListResponsePayload?) {
        txt_grand_total.text = AndroidUtils.getString(R.string.price_type) +
                " " + data?.data?.order?.totalAmount
        txt_grand_total_value.text = AndroidUtils.getString(R.string.price_type) +
                " " + data?.data?.order?.totalAmount
        txt_total_items.text = data?.data?.order?.orderItems?.size.toString()
        tv_tool_title.text = AndroidUtils.getString(R.string.shopping_cart) + "(" +
                data?.data?.order?.orderItems?.size.toString() + ")"
        txt_charges.text=data?.data?.order?.sourceAmount
        order = data?.data?.order
        txt_promotion.text=data?.data?.order?.vatCharge
       // setTotal()
        /*      if (data?.data?.order?.promotionAmount != null) {
                  txt_promotion.text = "(-)" + data?.data?.order?.promotionAmount
              }*/

        data?.data?.order?.orderItems?.let {

            if (it.size > 0) {

                llCheckout.visibility = View.VISIBLE


                preferenceManager?.savePreference(
                    Config.SharedPreferences.PROPERTY_IS_CART,
                    true
                )
                preferenceManager?.savePreference(
                    Config.SharedPreferences.PROPERTY_IS_CART_VALUE,
                    ""+it.size
                )
                //  orderList?.clear()
                orderList = data?.data?.order?.orderItems
                adapterCartListProducts?.submitList(orderList)
                adapterCartListProducts?.notifyDataSetChanged()

            } else {
                llCheckout.visibility = View.GONE
                preferenceManager?.savePreference(
                    Config.SharedPreferences.PROPERTY_IS_CART,
                    false
                )

                preferenceManager?.savePreference(
                    Config.SharedPreferences.PROPERTY_IS_CART_VALUE,
                    "0"
                )
                /* orderList = data?.data?.order?.orderItems
                 adapterCartListProducts?.submitList(orderList)
                 adapterCartListProducts?.notifyDataSetChanged()*/
            }
        }
    }

    private fun setTotal() {
        try {

            order?.sourceAmount?.let {
                shippingData?.setting?.let { it1 ->
                    if(it.toDouble()>0.0) {


                        if (it.toDouble() < it1.minimunAmount) {
                            txt_shipping_charge.text =
                                shippingData?.setting?.shippingCharge.toString()
                            /* var sum=it1.shippingCharge + it.toDouble()
                        txt_grand_total.text =
                            "" + sum
                        txt_grand_total_value.text = AndroidUtils.getString(R.string.price_type) +
                                " " + sum*/
                        } else {
                            txt_shipping_charge.text = "0.0"

                        }
                    }
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showData(data: ShippingResponsePayload?) {
        if (data!!.success) {
            shippingData = data?.data
            setTotal()


        } else {
            if (data?.message.equals("")) {
                data?.errors?.let {
                    if (it.size > 0) {
                        Logger.Debug("DEBUG", data.errors?.get(0).toString())
                        showSnackbar(
                            data.errors?.get(0)?.error,
                            false
                        )
                    }
                }
            } else {
                showSnackbar(
                    data?.message,
                    false
                )
            }
        }

    }

    private fun showData(data: OrderPlaceResponsePayload?) {
        if (data!!.success) {
            /* showSnackbar(
                 data?.message,
                 false
             )*/


            preferenceManager?.savePreference(
                Config.SharedPreferences.PROPERTY_IS_CART,
                false
            )
            preferenceManager?.savePreference(
                Config.SharedPreferences.PROPERTY_IS_CART_VALUE,
                "0"
            )
            customAlertDialog(data?.message)
            model.getCartList()
        } else {
            if (data?.message.equals("")) {
                data?.errors?.let {
                    if (it.size > 0) {
                        Logger.Debug("DEBUG", data.errors?.get(0).toString())
                        showSnackbar(
                            data.errors?.get(0)?.error,
                            false
                        )
                    }
                }
            } else {
                showSnackbar(
                    data?.message,
                    false
                )
            }
        }

    }

    private fun showData(data: AddProductResponsePayload?) {
        if (data!!.success) {
            if (isDelete) {
                var item = StoreProducts?.getInstance()?.getProduct(orderItemsItem?.itemMaster?.id)
                item?.inCart = false
               /* var i = 0

                while (i < item?.packs!!.size) {
                    if (item?.packs!!.get(i)?.id == orderItemsItem?.pack?.id) {
                        item?.packs!!.get(i)?.isSelected = false
                        item?.packs!!.get(i)?.inCart = false
                    }
                    i++
                }*/
                StoreProducts.getInstance().addProduct(item)
            }
            getData()
        } else {
            if (data?.message.equals("")) {
                data?.errors?.let {
                    if (it.size > 0) {
                        Logger.Debug("DEBUG", data.errors?.get(0).toString())
                        showSnackbar(
                            data.errors?.get(0)?.fieldLabel + " " + data.errors?.get(0)?.detail,
                            false
                        )
                    }
                }
            } else {
                showSnackbar(
                    data?.message,
                    false
                )
            }
        }

    }

    fun addProductToCart(objectAtPosition: OrderItemsItem, quantity: Int, isDelete: Int) {

        val ShopPg_AddToCart_event = AdjustEvent("lmxnag")
        ShopPg_AddToCart_event.setCallbackId("ShopPg_AddToCart_event");
        Adjust.trackEvent(ShopPg_AddToCart_event)

        if (NetworkUtil.isInternetAvailable(this)) {


            val productList = listOf(
                UpdateOrderItemsAttributesItem(
                    objectAtPosition.amount.toString(),
                    quantity,
                    0,
                    0,
                    objectAtPosition?.itemMaster?.id,
                    objectAtPosition.id, isDelete

                )
            )

            var order = UpdateProductRequestPayload(UpdateOrder(productList))

            model.updateProduct(order)

        }
    }

    fun getModes() {
        mode = null
        val modes = listOf(
            PaymentMode(
                "CASH", "cashed"
            ),
            PaymentMode(
                "CREDIT", "credited"
            )
        )
        val dataAdapter = AdapterPaymentMode(modes!!, this)
        let {
            paymentModeDialog = PaymentModeDialog(it, this, dataAdapter)
        }
        paymentModeDialog!!.show()
        paymentModeDialog!!.setCanceledOnTouchOutside(false)
    }

    fun customAlertDialog(m: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@CartDeatilActivity)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = inflater.inflate(R.layout.dialog_custom, null, false)

        builder.setView(customView)

        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        customView.tvMessage.text = m
        customView.tvTitle.text = AndroidUtils.getString(R.string.order_placed)
        customView.tvSuccess.text = AndroidUtils.getString(R.string.goto_orders)

        customView.tvSuccess.setOnClickListener({
            alertDialog.dismiss()
            startActivity(LandingNavigationActivity.getIntent(this, 3))

            // gotoLogin()
        })


    }

}
