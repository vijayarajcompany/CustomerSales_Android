package com.pepsidrc.ui.navigation.ui.more


import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent

import com.pepsidrc.R
import com.pepsidrc.base.BaseFragment
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.more.MoreItems
import com.pepsidrc.ui.credits.ActivityCredits
import com.pepsidrc.ui.faq.FAQActivity
import com.pepsidrc.ui.invoice.ActivityInvoice
import com.pepsidrc.ui.login.LoginActivity
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.ui.navigation.ui.more.adapter.AdapterMore
import com.pepsidrc.ui.profile.ActivityProfile
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.MiddleDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_more.*

/**
 * A simple [Fragment] subclass.
 */
class MoreFragment : BaseFragment<MoreViewModel>(MoreViewModel::class),
    AdapterViewClickListener<MoreItems> {
    private var adapterMore: AdapterMore? = null

    override fun getLayoutId() = R.layout.fragment_more


    override fun onClickAdapterView(
        objectAtPosition: MoreItems,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_MORE -> {

                activity?.let {


                    when (position) {
                        0 -> showProfile()
                        // 1 -> showInvoice()
                       // 1 -> showCredits()
                        1 -> showFAQ()
                        2 -> logout()
                        else -> {
                            //   showProfile()
                        }
                    }

                }

            }
        }
    }

    companion object {

        fun getInstance(instance: Int): MoreFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = MoreFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_more_items.layoutManager = manager
        rv_more_items.addItemDecoration(
            MiddleDividerItemDecoration(
                context!!,
                MiddleDividerItemDecoration.VERTICAL
            )
        )
        activity?.let {
            adapterMore = AdapterMore(this, it)

        }
        rv_more_items.adapter = adapterMore
        showData(getData())
    }


    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentMore()) {
            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.more))
            (activity as LandingNavigationActivity).setBack(true)
        }
    }

    private fun getData(): MutableList<MoreItems> {

        val moreItems: MutableList<MoreItems> = ArrayList()
        moreItems!!.add(
            MoreItems(
                R.drawable.ic_profile,
                AndroidUtils.getString(R.string.my_profile)
            )
        )
        /* moreItems!!.add(
             MoreItems(
                 R.drawable.ic_invoice,
                 AndroidUtils.getString(R.string.my_invoice)
             )
         )*/
      /*  moreItems!!.add(
            MoreItems(
                R.drawable.ic_credits,
                AndroidUtils.getString(R.string.my_credits)
            )
        )*/
        moreItems!!.add(
            MoreItems(
                R.drawable.question_mark,
                AndroidUtils.getString(R.string.faq)
            )
        )
        moreItems!!.add(MoreItems(R.drawable.logout, AndroidUtils.getString(R.string.logout)))

        return moreItems
    }

    private fun showData(data: MutableList<MoreItems>?) {
        adapterMore?.submitList(data)
        adapterMore?.notifyDataSetChanged()

    }

    private fun showProfile() {
        val MorePg_Profile_link_event = AdjustEvent("gnzrgt")
        Adjust.trackEvent(MorePg_Profile_link_event)

        activity.let {
            startActivity(
                ActivityProfile.getIntent(it),
                ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
            )
        }
    }

    private fun showInvoice() {
        activity.let {
            startActivity(
                ActivityInvoice.getIntent(it),
                ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
            )
        }
    }

    private fun showCredits() {
        activity.let {
            startActivity(
                ActivityCredits.getIntent(it),
                ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
            )
        }
    }
    private fun showFAQ() {
        activity.let {
            startActivity(
                FAQActivity.getIntent(it),
                ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
            )
        }
    }

    private fun logout() {
        activity?.let {
            val dialogBuilder = AlertDialog.Builder(it)

            // set message of alert dialog
            dialogBuilder.setMessage("Do you want to logout from this application ?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->

                    val adjustEvent = AdjustEvent("d8cu6p")
                    Adjust.trackEvent(adjustEvent)

                    var preferenceManager = PreferenceManager(it)
                    preferenceManager.savePreference(
                        Config.SharedPreferences.PROPERTY_LOGIN_PREF,
                        false
                    )
                    preferenceManager.savePreference(
                        Config.SharedPreferences.PROPERTY_IS_DIVISION_VALUE,
                        ""
                    )
                    preferenceManager.savePreference(
                        Config.SharedPreferences.PROPERTY_IS_DIVISION_SHOW,
                        false
                    )
                    startActivity(
                        LoginActivity.getIntent(it),
                        ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
                    )


                })
                // negative button text and action
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle(AndroidUtils.getString(R.string.logout))
            // show alert dialog
            alert.show()
        }
    }
}
