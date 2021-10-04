package com.pepsidrc.ui.navigation.ui.shop

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pepsidrc.util.UiUtils
import com.pepsidrc.R
import com.pepsidrc.base.BaseFragment
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.categories.CategoryResponse
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.trade_offer.OfferData
import com.pepsidrc.model.trade_offer.TradeOffersItem
import com.pepsidrc.ui.filter.FilterActivity
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.ui.navigation.ui.home.HomeViewModel
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterHomeCategories
import com.pepsidrc.ui.navigation.ui.shop.adapter.AdapterCategories
import com.pepsidrc.ui.navigation.ui.shop.adapter.AdapterOffer
import com.pepsidrc.utils.*
import com.pepsidrc.ui.navigation.ui.shop.dialog.OfferDialog
import com.pepsidrc.ui.navigation.ui.shop.dialog.OfferDialogListener
import com.pepsidrc.ui.subcategories.SubcategoriesFragment
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : BaseFragment<ShopViewModel>(ShopViewModel::class), AdapterViewClickListener<CategoriesItem>,
    AdapterOffer.RecyclerViewItemClickListener,OfferDialogListener {



    override fun clickOnItem(data: TradeOffersItem) {
        offer_id=data.id
        offerData=data
    }

    override fun applyOffer() {
       // mFragmentNavigation.pushFragment(SubcategoriesFragment.getInstance(mInt + 1,cat_id,offer_id,cat_name,offerData))
        mFragmentNavigation.pushFragment(SubcategoriesFragment.getInstance(mInt + 1,cat_id,offer_id,cat_name,TradeOffersItem()))

    }

    override fun onClickAdapterView(objectAtPosition: CategoriesItem, viewType: Int, position: Int) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY -> {

                this?.let {
                    cat_id=objectAtPosition.id
                    cat_name=objectAtPosition.name

                    applyOffer()
                  //  clickHere()
                }

            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_shop
    private var adapterCategories: AdapterCategories? = null
    internal var offerDialog: OfferDialog? = null
    internal var offerList: List<TradeOffersItem> ?=null
    internal var cat_id: Int?=-1
    internal var offer_id: Int?=-1
    internal var cat_name: String?=""
    lateinit var offerData: TradeOffersItem
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val manager = GridLayoutManager(context, 2)
        rv_category.layoutManager = manager
        rv_category.addItemDecoration(
            MiddleDividerItemDecoration(
                context!!,
                MiddleDividerItemDecoration.HORIZONTAL
            )
        )
        activity?.let {
            adapterCategories = AdapterCategories(this, it)

        }
        rv_category.adapter = adapterCategories
  /*      rlSort.setOnClickListener {
            (activity as LandingNavigationActivity).sortBy()
        }*/
        tvFilter.setOnClickListener {
         /*   activity!!.let {
                startActivity(FilterActivity.getIntent(it))
            }*/
        }
        subscribeLoading()
        subscribeUi()
        getData()
    }

    private fun getData() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getCategories()
          //  model.getTradeOffer()
        }
    }
    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentShop()) {
            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.shop))
            (activity as LandingNavigationActivity).setBack(false)
        }
    }

    private fun showData(data: CategoryResponse?) {
        adapterCategories?.submitList(data?.categories)

        adapterCategories?.notifyDataSetChanged()

    }
    fun clickHere() {
     /*   val items = arrayOf(
            "30% OFF",
            "20% OFF"

        )*/
        val dataAdapter = AdapterOffer(offerList!!, this)
        activity!!.let {
            offerDialog = OfferDialog(it,this, dataAdapter)
        }
        offerDialog!!.show()
        offerDialog!!.setCanceledOnTouchOutside(false)
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
        model.offerModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            offerList = ArrayList()
            offerList=it.data.tradeOffers
        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
    companion object {

        fun getInstance(instance: Int): ShopFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = ShopFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}