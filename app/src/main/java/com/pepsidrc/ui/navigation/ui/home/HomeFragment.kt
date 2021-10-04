package com.pepsidrc.ui.navigation.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pepsidrc.R
import com.pepsidrc.base.BaseFragment
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.categories.CategoryResponse
import com.pepsidrc.model.division.DivisionItem
import com.pepsidrc.model.division.DivisionListingResponse
import com.pepsidrc.model.home.banner.BannerResponsePayload
import com.pepsidrc.model.trade_offer.TradeOffersItem
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterDivision
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterHomeCategories
import com.pepsidrc.ui.navigation.ui.home.adapter.IndicatorPagerAdapter
import com.pepsidrc.ui.navigation.ui.home.dialog.DivisionDialog
import com.pepsidrc.ui.navigation.ui.home.dialog.DivisionDialogListener
import com.pepsidrc.ui.subcategories.SubcategoriesFragment
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_home.*
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class),
    AdapterViewClickListener<CategoriesItem>, AdapterDivision.RecyclerViewItemClickListener,
    DivisionDialogListener {
    override fun clickOnItem(data: DivisionItem) {
        division = data?.name

        tvSelectedDivision.text = data?.name



    }

    override fun applyDivision() {
        if (division == null) {
            showSnackbar(
                AndroidUtils.getString(R.string.please_select_division),
                false
            )
        } else {

            updateDivision()

            /* if (preferenceManager?.getStringPreference(
                     Config.SharedPreferences.PROPERTY_IS_DIVISION_VALUE,
                     ""
                 )
                     .equals("") || preferenceManager?.getStringPreference(
                     Config.SharedPreferences.PROPERTY_IS_DIVISION_VALUE,
                     ""
                 )
                     .equals(division)
             ) {
                 updateDivision()
             } else {
                 alert()
             }*/
        }
    }

    fun updateDivision() {
        preferenceManager?.savePreference(
            Config.SharedPreferences.PROPERTY_IS_DIVISION_VALUE,
            division
        )
        if (NetworkUtil.isInternetAvailable(activity)) {
            isGetData = true
            model.updateDivision(division)
        }
    }

    override fun onClickAdapterView(
        objectAtPosition: CategoriesItem,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY -> {

                this?.let {
                    cat_id = objectAtPosition.id
                    cat_name = objectAtPosition.name

                    val CategoryPg_Category_Tapped_event = AdjustEvent("yytyko")
                    CategoryPg_Category_Tapped_event.addCallbackParameter("CategoryPg_CategoryID_Tapped", cat_id.toString());
                    CategoryPg_Category_Tapped_event.addCallbackParameter("CategoryPg_CategoryName_Tapped", cat_name);

                    Adjust.trackEvent(CategoryPg_Category_Tapped_event)

                    mFragmentNavigation.pushFragment(
                        SubcategoriesFragment.getInstance(
                            mInt + 1,
                            cat_id,
                            0,
                            cat_name,
                            TradeOffersItem()
                        )
                    )

                }

            }


        }
    }


    override fun getLayoutId() = R.layout.fragment_home
    private var adapterCategories: AdapterHomeCategories? = null
    internal var cat_id: Int? = -1
    internal var cat_name: String? = ""
    internal var division: String? = null
    internal var isGetData: Boolean = false
    var divisionDialog: DivisionDialog? = null
    internal var divisionList: List<DivisionItem>? = null
    internal var catList: ArrayList<CategoriesItem>? = null
    internal var isViewMore: Boolean = false

    var preferenceManager: PreferenceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        divisionList = ArrayList()
        catList = ArrayList()
        isViewMore=false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            preferenceManager = PreferenceManager(it)
        }
        val manager = GridLayoutManager(context, 3)
        rv_cat.layoutManager = manager

        //  rv_products.addItemDecoration(MiddleDividerItemDecoration(context!!, MiddleDividerItemDecoration.HORIZONTAL))
        activity?.let {
            adapterCategories = AdapterHomeCategories(this, it)

        }
        rv_cat.adapter = adapterCategories


        rlDivision.setOnClickListener {

            getDivisions()
        }
        btnViewMore.setOnClickListener {
            val ViewMore_event = AdjustEvent("x268zt")
            Adjust.trackEvent(ViewMore_event)
            (activity as LandingNavigationActivity).switchTabShop()

        }

        if (preferenceManager!!.getBooleanPreference(Config.SharedPreferences.PROPERTY_IS_DIVISION_SHOW)) {
             getData()
         } else {
             getDivisions()

         }

        tvSelectedDivision.text = preferenceManager?.getStringPreference(
            Config.SharedPreferences.PROPERTY_IS_DIVISION_VALUE,
            ""
        )
        subscribeLoading()
        subscribeUi()
    }

    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragment()) {
            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.dubai_refreshments))
            (activity as LandingNavigationActivity).setBack(false)
            /* if (divisionList != null) {
                 divisionList?.let {
                     if (it != null && it?.size > 0) {
                         showDivisionDialog()
                     } else {
                         getDivisions()
                     }
                 }
             } else {
                 getDivisions()
             }*/
        }
    }

    private fun getDivisions() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getDivision()
        }
    }

    private fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getCategories()
        }
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getBanners()
        }
    }

    private fun showData(data: CategoryResponse?) {

        catList = data?.categories
        catList?.let {
            if (it.size > 12) {
                isViewMore=true
                it.subList(12,it.size).clear()
                adapterCategories?.submitList(it)

            }else{

                adapterCategories?.submitList(it)
            }
            if(isViewMore){
                btnViewMore.visibility= View.VISIBLE
            }else{
                btnViewMore.visibility= View.GONE

            }
            ViewCompat.setNestedScrollingEnabled(rv_cat, false)

            adapterCategories?.notifyDataSetChanged()
        }
    }

    public fun showDivisionDialog() {

        divisionList?.let {
            if (it?.size > 0) {


                /*   val items = arrayOf(
                "30% OFF",
                "20% OFF"

            )*/


                if (divisionDialog != null && divisionDialog!!.isShowing) {
                    divisionDialog?.dismiss()
                }
                val dataAdapter = AdapterDivision(divisionList!!, this)
                activity!!.let {
                    divisionDialog = DivisionDialog(it, this, dataAdapter)
                }
                if (divisionDialog != null && !divisionDialog!!.isShowing) {
                    divisionDialog!!.show()

                    divisionDialog!!.setCanceledOnTouchOutside(false)
                    divisionDialog!!.setCancelable(false)

                }

            } else {
                getDivisions()
            }

        }
    }

    private fun showDivisions(data: DivisionListingResponse?) {
        if (!preferenceManager!!.getBooleanPreference(Config.SharedPreferences.PROPERTY_IS_DIVISION_SHOW)) {

            divisionList?.let {
                if (it.size == 0) {
                    Logger.Debug("DEBUG", data.toString())
                    if (data?.success!!) {
                        divisionList = data?.data?.division

                        showDivisionDialog()
                    }
                }
            }
        }
    }

    private fun showBanner(data: BannerResponsePayload?) {
        try {
            with(view_pager) {
                adapter = IndicatorPagerAdapter(data?.data?.banner!!)
                //    setPageTransformer(true, ZoomOutPageTransformer())
                worm_dots_indicator.setViewPager(this)
                setInterval(2000)
                setDirection(AutoScrollViewPager.Direction.RIGHT)
                setCycle(true)
                //  setBorderAnimation(true)
                //    setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.TO_PARENT)
                startAutoScroll()
            }
        } catch (e: Exception) {

        }
    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.let {
                UiUtils.showInternetDialog(activity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.categoryModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

                showData(it.data)

        })
        model.bannerModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showBanner(it)
        })
        model.divisionModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showDivisions(it)

        })
        model.updatedivisionModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.success) {
                (activity as LandingNavigationActivity).setCounter(
                    true,
                    it?.data?.user?.cart_item_count
                )
                preferenceManager?.savePreference(
                    Config.SharedPreferences.PROPERTY_IS_DIVISION_SHOW,
                    true
                )
                if (isGetData) {
                    isGetData = false
                    getData()
                }
            }

        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    companion object {

        fun getInstance(instance: Int): HomeFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    private fun alert() {
        activity?.let {
            val dialogBuilder = AlertDialog.Builder(it)

            // set message of alert dialog
            dialogBuilder.setMessage("If you change the division type all products will be removed from the shopping cart.")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Continue", DialogInterface.OnClickListener { dialog, id ->
                    updateDivision()

                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                    divisionDialog?.dismiss()
                    if (catList?.size == 0) {
                        division = preferenceManager?.getStringPreference(
                            Config.SharedPreferences.PROPERTY_IS_DIVISION_VALUE,
                            ""
                        )
                        updateDivision()
                    }
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            //   alert.setTitle(AndroidUtils.getString(R.string.logout))
            // show alert dialog
            alert.show()

        }
    }

}