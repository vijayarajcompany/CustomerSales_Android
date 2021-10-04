package com.pepsidrc.ui.search

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.base.StoreProducts
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.callbacks.AdapterViewPacksClickListener
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.Order
import com.pepsidrc.model.cart.OrderItemsAttributesItem
import com.pepsidrc.model.product.ItemMastersItem
import com.pepsidrc.model.product.PacksItem
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.ui.productDetail.ProductDetailActivity
import com.pepsidrc.ui.products.ProductsViewModel
import com.pepsidrc.ui.products.adapter.AdapterShopProducts
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.app_custom_tool_bar_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent

class SearchActivity : BaseActivity<SearchViewModel>(SearchViewModel::class),
    AdapterViewClickListener<ItemMastersItem>, AdapterViewPacksClickListener<PacksItem> {
    override fun onClickAdapterView(
        objectAtPosition: ItemMastersItem,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_ADD_TO_CART_PRODUCT -> {

                let {
                    itemMastersItem = StoreProducts.getInstance().getProduct(objectAtPosition.id)!!
                    if (itemMastersItem == null) {
                        itemMastersItem = productsList?.get(position)!!
                    }
                    addProductToCart(itemMastersItem)

                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT -> {

                let {
                    UiUtils.hideSoftKeyboard(it)
                    startActivity(
                        ProductDetailActivity.getIntent(
                            it,
                            objectAtPosition,
                            objectAtPosition?.name
                        ),
                        ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                    )
                }
            }
        }
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

    override fun layout(): Int = R.layout.activity_search

    companion object {
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, SearchActivity::class.java)

        }
    }

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var productsList: ArrayList<ItemMastersItem>? = ArrayList()
    private var adapterShopProducts: AdapterShopProducts? = null
    lateinit var itemMastersItem: ItemMastersItem
    val modelProduct: ProductsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val SearchPg_event = AdjustEvent("86exm3")
        Adjust.trackEvent(SearchPg_event)

        val manager = GridLayoutManager(this, 2)
        rv_products.layoutManager = manager
        rv_products.addItemDecoration(
            MiddleDividerItemDecoration(
                this!!,
                MiddleDividerItemDecoration.HORIZONTAL
            )
        )

        let {
            adapterShopProducts = AdapterShopProducts(this, this, it)
        }
        rv_products.adapter = adapterShopProducts
        sv_product.requestFocus()
        // this.let { UiUtils.showSoftKeyboard(it,sv_product) }
        fl_left_img_container_search.setOnClickListener {

            onBackPressed()
        }
        sv_product.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (NetworkUtil.isInternetAvailable(this@SearchActivity)) {
                    model.searchProduct(query)
                }
                return false
            }

        })
        subscribeUi()
        subscribeLoading()

    }


    fun addProductToCart(objectAtPosition: ItemMastersItem) {
  //      var i = 0
 //       var isPackSelected = false

        if (NetworkUtil.isInternetAvailable(this)) {


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

                modelProduct.addProduct(order)

            }


/*
        if (NetworkUtil.isInternetAvailable(this)) {

            while (i < objectAtPosition?.packs!!.size) {

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

                    modelProduct.addProduct(order)
                }
                i++
            }
            if (!isPackSelected) {
                showSnackbar(
                    AndroidUtils.getString(R.string.please_select_pack),
                    false
                )
            }
        }
*/

        }

    private fun subscribeUi() {
        modelProduct.addProductModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })
        model.productSearchModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })

    }

    private fun showData(data: ProductResponsePayload?) {

        StoreProducts.getInstance().saveProducts(data?.data?.itemMasters)
        productsList?.let {


            data?.data?.itemMasters?.let {
                if (it.size > 0) {
                    productsList = data?.data?.itemMasters
                    adapterShopProducts?.submitList(productsList)
                    adapterShopProducts?.notifyDataSetChanged()
                } else {
                    showSnackbar(AndroidUtils.getString(R.string.no_product_found), false)

                }

            }
        }
    }

    private fun showData(data: AddProductResponsePayload?) {
        //  adapterShopProducts?.submitList(data?.data?.itemMasters)
//        showSnackbar(data?.errors?.get(0)?.fieldLabel + " " + data?.errors?.get(0)?.detail, false)
        if (data!!.success) {
            let {

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


}
