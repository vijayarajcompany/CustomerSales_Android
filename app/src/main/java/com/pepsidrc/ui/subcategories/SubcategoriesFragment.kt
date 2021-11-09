package com.pepsidrc.ui.subcategories


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.pepsidrc.util.UiUtils

import com.pepsidrc.R
import com.pepsidrc.base.BaseFragment
import com.pepsidrc.base.StoreProducts
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.model.subcategories.SubCategoriesData
import com.pepsidrc.model.subcategories.SubcategoriesItem
import com.pepsidrc.model.trade_offer.TradeOffersItem
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.ui.products.ProductFragment
import com.pepsidrc.ui.subcategories.adapter.AdapterSubCategories
import com.pepsidrc.utils.*
import kotlinx.android.synthetic.main.fragment_subcategories.*
import kotlinx.android.synthetic.main.layout_applied_offer.*

/**
 * A simple [Fragment] subclass.
 */
class SubcategoriesFragment : BaseFragment<SubCategoriesViewModel>(SubCategoriesViewModel::class), AdapterViewClickListener<SubcategoriesItem> {
    override fun onClickAdapterView(objectAtPosition: SubcategoriesItem, viewType: Int, position: Int) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_SUB_CATEGORY -> {

                val SubCategoryPg_Category_Tapped_event = AdjustEvent("eu4tvj")
                SubCategoryPg_Category_Tapped_event.setCallbackId("SubCategoryPg_Category_Tapped_event");
                SubCategoryPg_Category_Tapped_event.addCallbackParameter("SubCategoryPg_CategoryName_Tapped", objectAtPosition.name);
                SubCategoryPg_Category_Tapped_event.addCallbackParameter("SubCategoryPg_CategoryID_Tapped", objectAtPosition.id.toString());
                Adjust.trackEvent(SubCategoryPg_Category_Tapped_event)

              /*  this?.let {
                    (activity as LandingNavigationActivity).pushFragments(LandingNavigationActivity.tabs.TAB_SHOP
                    , ProductFragment.getInstance(),true)
                }*/
              //  mFragmentNavigation.pushFragment(ProductFragment.getInstance(mInt + 1,objectAtPosition,cat_id,cat_name,tradeOffersItem))
                mFragmentNavigation.pushFragment(ProductFragment.getInstance(mInt + 1,objectAtPosition,cat_id,cat_name,TradeOffersItem()))

            }
        }
    }
    // var tradeOffersItem: TradeOffersItem?=null
    private var adapterSubCategories: AdapterSubCategories? = null
    internal var cat_id: Int?=-1
    internal var offer_id: Int?=-1
    internal var cat_name: String?=""

    override fun getLayoutId() = R.layout.fragment_subcategories
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {

            cat_id = arguments?.getInt(KEY_CATEGORY_ID, 0)
            offer_id =arguments?.getInt(KEY_OFFER_ID, 0)
            cat_name =arguments?.getString(KEY_CATEGORY_NAME, "")
         //   tradeOffersItem=arguments?.getParcelable(KEY_OFFER_DATA)
            tvCategoryName.text=cat_name
           // tvOffer.text=tradeOffersItem?.tradeOfferType
        }

        val manager = GridLayoutManager(context, 2)
        rv_sub_category.layoutManager = manager
        rv_sub_category.addItemDecoration(
            MiddleDividerItemDecoration(
                context!!,
                MiddleDividerItemDecoration.HORIZONTAL
            )
        )
        activity?.let {
            adapterSubCategories = AdapterSubCategories(this, it)

        }
        rv_sub_category.adapter = adapterSubCategories
      /*  rlSortSubCategory.setOnClickListener {
            (activity as LandingNavigationActivity).sortBy()
        }*/
        tvFilterSubCategory.setOnClickListener {
           /* activity!!.let {
                startActivity(FilterActivity.getIntent(it))
            }*/
        }

        subscribeUi()
        subscribeLoading()
        StoreProducts.getInstance().clear()
        getData()
    }

    private fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getSubCategories(cat_id,offer_id)
        }
       /* var productsResponse = ProductsResponse()

        val product: MutableList<Product> = ArrayList()
        product!!.add(Product(1, "Pepsi Lemon Drink", "AED 5.25"))
        product!!.add(Product(2, "Pepsi Lemon Drink", "AED 5.25"))
        product!!.add(Product(3, "Pepsi Lemon Drink", "AED 5.25"))
        product!!.add(Product(4, "Pepsi Lemon Drink", "AED 5.25"))
        product!!.add(Product(5, "Pepsi Lemon Drink", "AED 5.25"))
        product!!.add(Product(6, "Pepsi Lemon Drink", "AED 5.25"))
        product!!.add(Product(7, "Pepsi Lemon Drink", "AED 5.25"))
        productsResponse!!.data = product
        return productsResponse*/


    }
    override fun onResume() {
        super.onResume()
        (activity as LandingNavigationActivity).setTitleOnBar(cat_name)
        (activity as LandingNavigationActivity).setBack(true)

    }
    private fun showData(data: SubCategoriesData?) {
        adapterSubCategories?.submitList(data?.subcategories)
        adapterSubCategories?.notifyDataSetChanged()

    }

    private fun subscribeUi() {
        model.subCategoriesModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it.data)
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
                UiUtils.showInternetDialog(activity, R.string.something_went_wrong)
            }
        })
    }
    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
    companion object {
        const val KEY_CATEGORY_ID = "KEY_CATEGORY_ID"
        const val KEY_OFFER_ID = "KEY_OFFER_ID"
        const val KEY_CATEGORY_NAME = "KEY_CATEGORY_NAME"
        const val KEY_OFFER_DATA = "KEY_OFFER_DATA"

        fun getInstance(instance: Int,cat_id: Int?,offer_id: Int?,cat_name: String?,tradeOffersItem: TradeOffersItem): SubcategoriesFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            bundle.putInt(KEY_CATEGORY_ID, cat_id!!)
            bundle.putInt(KEY_OFFER_ID, offer_id!!)
            bundle.putString(KEY_CATEGORY_NAME, cat_name!!)
         //   bundle.putParcelable(KEY_OFFER_DATA, tradeOffersItem)

            val fragment = SubcategoriesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
