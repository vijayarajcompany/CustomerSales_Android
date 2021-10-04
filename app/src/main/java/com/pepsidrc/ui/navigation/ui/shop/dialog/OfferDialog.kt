package com.pepsidrc.ui.navigation.ui.shop.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.R
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.ui.subcategories.SubcategoriesFragment
import kotlinx.android.synthetic.main.offer_dialog.*

class OfferDialog(var activity: Activity, var offerDialogListener: OfferDialogListener, internal var adapter: RecyclerView.Adapter<*>) : Dialog(activity),
    View.OnClickListener{



    var dialog: Dialog? = null

    internal var recyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.offer_dialog)
        recyclerView = rv_offer
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = adapter

        btnApplyOffer.setOnClickListener(this)

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnApplyOffer -> {
                /*(activity as LandingNavigationActivity).pushFragment(
                    SubcategoriesFragment.getInstance())*/
                    offerDialogListener.applyOffer()

              /*  var fragment = sup.findFragmentById(R.id.frameLayoutCW) as WebViewFragment
                fragment.callAboutUsActivity()*/

            }
            else -> {
            }
        }
        dismiss()
    }
}