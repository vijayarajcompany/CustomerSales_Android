package com.pepsidrc.ui.cart.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.R
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent

import kotlinx.android.synthetic.main.payment_option_dialog.*

class PaymentModeDialog(var activity: Activity, var payment_modeDialogListener: PaymentModeDialogListener, internal var adapter: RecyclerView.Adapter<*>) : Dialog(activity),
    View.OnClickListener{



    var dialog: Dialog? = null

    internal var recyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.payment_option_dialog)
        recyclerView = rv_payment_mode
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = adapter

        btnPlaceOrder.setOnClickListener(this)

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnPlaceOrder -> {

                val OrderPg_PlaceOrder_event = AdjustEvent("va0e37")
                Adjust.trackEvent(OrderPg_PlaceOrder_event)


                /*(activity as LandingNavigationActivity).pushFragment(
                    SubcategoriesFragment.getInstance())*/
                    payment_modeDialogListener.placeOrder()

              /*  var fragment = sup.findFragmentById(R.id.frameLayoutCW) as WebViewFragment
                fragment.callAboutUsActivity()*/

            }
            else -> {
            }
        }
        dismiss()
    }
}