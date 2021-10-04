package com.pepsidrc.ui.place_order

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepsidrc.R
import com.pepsidrc.address.AddAddressRequestPayload
import com.pepsidrc.address.AddressRequest
import com.pepsidrc.address.AddressResponsePayload
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.PaymentMode
import com.pepsidrc.model.place_order.OrderPlaceResponsePayload
import com.pepsidrc.ui.cart.CartViewModel
import com.pepsidrc.ui.cart.adapter.AdapterPaymentMode
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.Logger
import kotlinx.android.synthetic.main.activity_place_order.*
import kotlinx.android.synthetic.main.app_custom_tool_bar_filter.*
import kotlinx.android.synthetic.main.dialog_custom.view.*

class PlaceOrderActivity : BaseActivity<CartViewModel>(CartViewModel::class),
    AdapterPaymentMode.RecyclerViewItemClickListener {

    override fun layout(): Int = R.layout.activity_place_order

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickOnItem(data: PaymentMode) {
        mode = data.value
    }

    internal var mode: String? = "cashed"
    var address1: String = ""
    var amount: String = ""

    var preferenceManager: PreferenceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager(this)

        var manager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        intent?.run {

            address1 = getStringExtra(KEY_ADDRESS1).toString()
            amount = getStringExtra(KEY_AMOUNT).toString()
            tvAddressValue1.text = address1
            txt_grand_total.text = amount
        }
        subscribeUi()
        subscribeLoading()
        btnPlaceOrder.setOnClickListener {

         //   mode="cashed"
            val validateMobileError =
                AndroidUtils.mobilePasswordValidation(etMobileNUmber.text.toString())
            val validateMobileCountryError =
                AndroidUtils.validateCode(etMobileNUmberCountryCode.text.toString())
            val validateAddressError =
                AndroidUtils.validateAddress(etaddress2.text.toString())

            if (TextUtils.isEmpty(validateMobileError) && TextUtils.isEmpty(
                    validateMobileCountryError) && TextUtils.isEmpty(
                    validateAddressError
                )
            ) {
                if (mode == null) {
                    showSnackbar(
                        AndroidUtils.getString(R.string.please_select_mode),
                        false
                    )
                } else {
                    val address = AddressRequest(
                        address1, etaddress2.text.toString(),
                        etMobileNUmberCountryCode.text.toString() + "" + etMobileNUmber.text.toString()
                    )
                    val payLoadAddress = AddAddressRequestPayload(address)
                    model.updateAddress(payLoadAddress)
                }
            } else {
                etaddress2.error = validateAddressError
                etMobileNUmber.error = validateMobileError
                etMobileNUmberCountryCode.error = validateMobileCountryError

            }
        }
        etaddress2.requestFocus()
        etMobileNUmberCountryCode.setText("+971-")
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        rv_payment_mode?.layoutManager = manager
        getModes()
    }

    private fun subscribeUi() {

        model.placeOrderModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })
        model.addAddressModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })
    }

    private fun showData(data: AddressResponsePayload?) {
        if (data!!.success) {

            model.placeOrder(mode)
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

    fun customAlertDialog(m: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@PlaceOrderActivity)
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

    public override fun onResume() {
        super.onResume()
        btn_clear.visibility = View.GONE
        btn_clear.text = AndroidUtils.getString(R.string.next)
        tv_tool_title.text = AndroidUtils.getString(R.string.step2)

    }

    fun getModes() {
        mode = null
        val modes = listOf(
            PaymentMode(
                "CASH", "cashed"
            )/*,
            PaymentMode(
                "CREDIT", "credited"
            )*/
        )
        val dataAdapter = AdapterPaymentMode(modes!!, this)

        rv_payment_mode?.adapter = dataAdapter
    }

    companion object {
        const val KEY_ADDRESS1 = "KEY_ADDRESS1"
        const val KEY_ADDRESS2 = "KEY_ADDRESS2"
        const val KEY_AMOUNT = "KEY_AMOUNT"

        fun getIntent(
            context: Context?,
            address1: String?,
            amount: String?
        ): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, PlaceOrderActivity::class.java).putExtra(
                KEY_ADDRESS1,
                address1
            ).putExtra(KEY_AMOUNT, amount)

        }
    }

}
