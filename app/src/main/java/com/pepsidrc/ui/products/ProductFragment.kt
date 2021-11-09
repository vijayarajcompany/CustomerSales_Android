package com.pepsidrc.ui.products


import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.pepsidrc.R
import com.pepsidrc.base.BaseFragment
import com.pepsidrc.base.StoreProducts
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.callbacks.AdapterViewPacksClickListener
import com.pepsidrc.common.PaginationScrollListener
import com.pepsidrc.interfaces.SortImpl
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.brand.BrandItem
import com.pepsidrc.model.brand.BrandListResponse
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.Order
import com.pepsidrc.model.cart.OrderItemsAttributesItem
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.categories.CategoryResponse
import com.pepsidrc.model.filter.FilterDefaultMultipleListModel
import com.pepsidrc.model.product.ItemMastersItem
import com.pepsidrc.model.product.PacksItem
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.model.subcategories.SubcategoriesItem
import com.pepsidrc.model.trade_offer.TradeOffersItem
import com.pepsidrc.ui.filter.FilterActivity
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.ui.navigation.ui.shop.ShopViewModel
import com.pepsidrc.ui.productDetail.ProductDetailActivity
import com.pepsidrc.ui.products.adapter.AdapterShopProducts
import com.pepsidrc.ui.subcategories.SubcategoriesFragment
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.*
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.layout_applied_offer.*
import org.koin.androidx.viewmodel.ext.android.viewModel


import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent


/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : BaseFragment<ProductsViewModel>(ProductsViewModel::class),
    AdapterViewClickListener<ItemMastersItem>, AdapterViewPacksClickListener<PacksItem>, SortImpl {
    override fun sortBy(sort: String) {



        when (sort) {
            Config.Constants.asc -> {
                sort_selected=Config.Constants.asc
                sort_by= Config.Constants.asc
                column="price"
            }
            Config.Constants.desc -> {
                sort_selected=Config.Constants.desc

                sort_by= Config.Constants.desc
                column="price"
            }
            Config.Constants.atoz -> {
                sort_selected=Config.Constants.atoz

                sort_by= Config.Constants.asc

                column="itemdescription"
            }
            Config.Constants.ztoa -> {
                sort_selected=Config.Constants.ztoa

                sort_by= Config.Constants.desc
                column="itemdescription"
            }
        }
        page=1
        getData()
        /*Toast.makeText(
            context, sort,
            Toast.LENGTH_SHORT
        ).show()*/
    }

    override fun onClickPacksAdapterView(
        objectAtPosition: PacksItem,
        viewType: Int,
        position: Int
    ) {
        if (!productsList?.get(objectAtPosition?.productPosition)!!.inCart) {
            var i = 0

            while (i < productsList?.get(objectAtPosition?.productPosition)?.packs!!.size) {
                productsList?.get(objectAtPosition?.productPosition)?.packs?.get(i)?.isSelected =
                    false
                i++
            }
            productsList?.get(objectAtPosition?.productPosition)?.packs?.get(position)?.isSelected =
                true
            adapterShopProducts?.submitList(productsList)
            adapterShopProducts?.notifyDataSetChanged()
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClickAdapterView(
        objectAtPosition: ItemMastersItem,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_ADD_TO_CART_PRODUCT -> {

                activity?.let {
                    itemMastersItem = StoreProducts.getInstance().getProduct(objectAtPosition.id)!!
                    if (itemMastersItem == null) {
                        itemMastersItem = productsList?.get(position)!!
                    }
                    addProductToCart(itemMastersItem)

                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT -> {

                activity?.let {
                    UiUtils.hideSoftKeyboard(it)

                    val ProductPg_Product_Tapped_event = AdjustEvent("w7j2eh")
                    ProductPg_Product_Tapped_event.addCallbackParameter("ProductPg_ProductName_Tapped", objectAtPosition.name);
                    ProductPg_Product_Tapped_event.addCallbackParameter("ProductPg_ProductID_Tapped", objectAtPosition.id.toString());
                    Adjust.trackEvent(ProductPg_Product_Tapped_event)

                    startActivity(
                        ProductDetailActivity.getIntent(
                            it,
                            objectAtPosition,
                            subcategoriesItem?.name
                        ),
                        ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                    )
                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_PACKS -> {

                activity?.let {
                    UiUtils.hideSoftKeyboard(it)
                    startActivity(
                        ProductDetailActivity.getIntent(
                            it,
                            objectAtPosition,
                            subcategoriesItem?.name
                        ),
                        ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                    )
                }
            }
        }
    }

    val modelShop: ShopViewModel by viewModel()

    private var adapterShopProducts: AdapterShopProducts? = null

    override fun getLayoutId() = R.layout.fragment_product
    lateinit var subcategoriesItem: SubcategoriesItem
    // var tradeOffersItem: TradeOffersItem? = null
    internal var cat_id: Int? = -1
    internal var page: Int = 1

    internal var cat_name: String? = ""
    internal var isFilter: Boolean = false
    internal var minPrice: String? = "0"
    internal var maxPrice: String? = "200"
    lateinit var itemMastersItem: ItemMastersItem
    internal var column: String = "price"
    internal var sort_by: String = "desc"
    internal var sort_selected: String = ""

    private var productsList: ArrayList<ItemMastersItem>? = ArrayList()
    private var catList: ArrayList<CategoriesItem>? = ArrayList()
    private var brandList: ArrayList<BrandItem>? = ArrayList()

    val REQUEST_CODE = 11
    private var categoryMultipleListModels = ArrayList<FilterDefaultMultipleListModel>()
    private var brandMultipleListModels = ArrayList<FilterDefaultMultipleListModel>()

    private var categorySelectedIds = ArrayList<Int?>()
    private var brandSelectedIds = ArrayList<Int?>()
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var currentPage: Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val manager = GridLayoutManager(context, 2)
        rv_products.layoutManager = manager
        rv_products.addItemDecoration(
            MiddleDividerItemDecoration(
                context!!,
                MiddleDividerItemDecoration.HORIZONTAL
            )
        )

        rv_products?.addOnScrollListener(object : PaginationScrollListener(manager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                //you have to call loadmore items to get more data
                getMoreItems()
            }
        })

        activity?.let {
            adapterShopProducts = AdapterShopProducts(this, this, it)
            subcategoriesItem = arguments?.getParcelable(KEY_SUBCATEGORY_DATA)!!

            cat_id = arguments?.getInt(SubcategoriesFragment.KEY_CATEGORY_ID, 0)
            cat_name = arguments?.getString(SubcategoriesFragment.KEY_CATEGORY_NAME, "")
            //  tradeOffersItem = arguments?.getParcelable(SubcategoriesFragment.KEY_OFFER_DATA)
            tvCategoryName.text = cat_name
            //     tvOffer.text = tradeOffersItem?.tradeOfferType
        }
        rv_products.adapter = adapterShopProducts
        subscribeUi()
        subscribeLoading()

        // getCategories
        if (NetworkUtil.isInternetAvailable(activity)) {
            modelShop.getCategories()
            model.getBrands()
        }
        rlSortProduct.setOnClickListener {
            (activity as LandingNavigationActivity).sortBy(this,sort_selected)
        }
        tvFilterProduct.setOnClickListener {
            activity?.let {
                startActivityForResult(
                    FilterActivity.getIntent(
                        it,
                        catList, brandList,
                        categoryMultipleListModels, brandMultipleListModels, minPrice!!, maxPrice!!
                    ), REQUEST_CODE
                )
            }
        }
        StoreProducts.getInstance().clear()
        getData()
    }
    fun getMoreItems() {
        //after fetching your data assuming you have fetched list in your
        // recyclerview adapter assuming your recyclerview adapter is
        //rvAdapter
      //  after getting your data you have to assign false to isLoading
        isLoading = false

            if(!isLastPage) {
                page++
                getData()
            }
    }
    private fun getData() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            //    model.getProducts(subcategoriesItem.id)
            model.getProductsFiters(
                subcategoriesItem.id,
                categorySelectedIds,
                brandSelectedIds,
                minPrice!!.toInt(),
                maxPrice!!.toInt(),
                sort_by,
                column,
                10,page!!
            )

        }
    }

    fun addProductToCart(objectAtPosition: ItemMastersItem) {
     //   var i = 0
    //    var isPackSelected = false

        val ProductPg_AddToCart_Btn_event = AdjustEvent("69f2l5")
        ProductPg_AddToCart_Btn_event.addCallbackParameter("ProductPg_AddToCart_Tapped_ID", objectAtPosition.name);
        Adjust.trackEvent(ProductPg_AddToCart_Btn_event)

        if (NetworkUtil.isInternetAvailable(activity)) {

            val productList = listOf(
                OrderItemsAttributesItem(
                    objectAtPosition.price.toString(),
                    1,
                    0,
                    0,
                    objectAtPosition.id
                )
            )
            var order = AddProductRequestPayload(Order(productList))

            model.addProduct(order)

          /*  while (i < objectAtPosition?.packs!!.size) {

                if (objectAtPosition?.packs!!.get(i)?.isSelected) {
                    val productList = listOf(
                        OrderItemsAttributesItem(
                            objectAtPosition.price.toString(),
                            1,
                            objectAtPosition?.packs!!.get(i)?.id,
                            objectAtPosition?.packs!!.get(i)?.count,
                            objectAtPosition.id
                        )
                    )
                    isPackSelected = true
                    var order = AddProductRequestPayload(Order(productList))

                    model.addProduct(order)
                }
                i++
            }
            if (!isPackSelected) {
                showSnackbar(
                    AndroidUtils.getString(R.string.please_select_pack),
                    false
                )
            }*/
        }

    }

    private fun showData(data: ProductResponsePayload?) {

        StoreProducts.getInstance().saveProducts(data?.data?.itemMasters)
        productsList?.let {

            if (it.size > 0 && page != 1) {
                data?.data?.itemMasters?.let {
                    if(it.size>0) {
                        for (i in data?.data?.itemMasters!!.indices) {
                            productsList?.add(data?.data?.itemMasters!!.get(i))

                        }
                    }
                }
                adapterShopProducts?.submitList(productsList)
                adapterShopProducts?.notifyDataSetChanged()
            }
            else{
                productsList=data?.data?.itemMasters
                adapterShopProducts?.submitList(productsList)
                adapterShopProducts?.notifyDataSetChanged()
            }

            if(it.size>=data?.meta?.pagination?.totalCount!!){
                isLastPage=true

            }
            else{
                isLastPage=false

            }
        }
        /*if(data?.meta?.pagination?.currentPage!!<data?.meta?.pagination?.totalPages!!){
            isLastPage=false
        }
        else{
            isLastPage=true
        }*/
        isLoading=false
        /*val handler = Handler()
        handler.postDelayed({
            hideProgressDialog()
        }, 2000)*/

    }

    private fun showData(data: AddProductResponsePayload?) {
        //  adapterShopProducts?.submitList(data?.data?.itemMasters)
//        showSnackbar(data?.errors?.get(0)?.fieldLabel + " " + data?.errors?.get(0)?.detail, false)
        if (data!!.success) {
            activity?.let {
                (activity as LandingNavigationActivity).setCounter(true,data?.data?.order?.cart_item_count)


                var preferenceManager = PreferenceManager(it)

                preferenceManager.savePreference(
                    Config.SharedPreferences.PROPERTY_IS_CART,
                    true
                )
                preferenceManager.savePreference(
                    Config.SharedPreferences.PROPERTY_IS_CART_VALUE,
                    data?.data?.order?.cart_item_count
                )
            }
            itemMastersItem.inCart = true
            StoreProducts.getInstance().addProduct(itemMastersItem)
            adapterShopProducts?.notifyDataSetChanged()
            showSnackbar(data?.message, false)
        } else {
            showSnackbar(
                data?.errors?.get(0)?.fieldLabel + " " + data?.errors?.get(0)?.detail,
                false
            )
        }
        //getData()
        //   adapterShopProducts?.notifyDataSetChanged()

    }

    private fun getCat(data: CategoryResponse?) {
        catList = data?.categories
    }

    private fun getBrands(data: BrandListResponse?) {
        brandList = data?.data?.brand
    }

    override fun onResume() {
        super.onResume()
        (activity as LandingNavigationActivity).setTitleOnBar(subcategoriesItem?.name)
        (activity as LandingNavigationActivity).setBack(true)
        if (isFilter) {
            getData()
        } else {
            productsList?.let {

                if (it.size > 0) {

                    adapterShopProducts?.notifyDataSetChanged()

                }
            }
        }

    }

    companion object {
        const val KEY_SUBCATEGORY_DATA = "KEY_SUBCATEGORY_DATA"
        const val KEY_CATEGORY_ID = "KEY_CATEGORY_ID"
        const val KEY_CATEGORY_NAME = "KEY_CATEGORY_NAME"
        const val KEY_OFFER_DATA = "KEY_OFFER_DATA"
        fun getInstance(
            instance: Int,
            subcatItem: SubcategoriesItem,
            cat_id: Int?,
            cat_name: String?,
            tradeOffersItem: TradeOffersItem?
        ): ProductFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            bundle.putParcelable(KEY_SUBCATEGORY_DATA, subcatItem)
            bundle.putInt(KEY_CATEGORY_ID, cat_id!!)
            bundle.putString(KEY_CATEGORY_NAME, cat_name!!)
            // bundle.putParcelable(KEY_OFFER_DATA, tradeOffersItem)
            val fragment = ProductFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun subscribeUi() {
        model.addProductModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })
        model.productModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })
        modelShop.categoryModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            getCat(it?.data)
        })
        model.brandModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            getBrands(it)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE ->
                if (resultCode == Activity.RESULT_OK) {
                    categoryMultipleListModels =
                        data!!.getParcelableArrayListExtra<FilterDefaultMultipleListModel>("catModel")!!
                    categorySelectedIds = data!!.getIntegerArrayListExtra("catSelected")!!

                    brandMultipleListModels =
                        data!!.getParcelableArrayListExtra<FilterDefaultMultipleListModel>("brandModel")!!
                    brandSelectedIds = data!!.getIntegerArrayListExtra("brandSelected")!!
                    minPrice = data!!.getStringExtra("minPrice")
                    maxPrice = data!!.getStringExtra("maxPrice")

                    isFilter = true
                    page=1
                    productsList?.let {

                        if (it.size > 0) {

                            productsList?.clear()

                        }
                    }
                }

        }
    }

}
