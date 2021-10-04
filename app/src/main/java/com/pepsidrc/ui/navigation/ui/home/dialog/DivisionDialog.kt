package com.pepsidrc.ui.navigation.ui.home.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.R
import kotlinx.android.synthetic.main.offer_dialog.*
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent


class DivisionDialog(var activity: Activity, var divisionDialogListener: DivisionDialogListener, internal var adapter: RecyclerView.Adapter<*>) : Dialog(activity),
    View.OnClickListener{



    var dialog: Dialog? = null

    internal var recyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.division_dialog)
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
                    divisionDialogListener.applyDivision()

              /*  var fragment = sup.findFragmentById(R.id.frameLayoutCW) as WebViewFragment
                fragment.callAboutUsActivity()*/

            }
            else -> {
            }
        }
        dismiss()
    }
}