package com.pepsidrc.ui.productDetail

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.drawable.ScalingUtils
import com.pepsidrc.util.UiUtils
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.base.StoreProducts
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.callbacks.AdapterViewPacksClickListener
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.Order
import com.pepsidrc.model.cart.OrderItemsAttributesItem
import com.pepsidrc.model.product.ImagesItem
import com.pepsidrc.model.product.ItemMastersItem
import com.pepsidrc.model.product.PacksItem
import com.pepsidrc.ui.cart.CartDeatilActivity
import com.pepsidrc.ui.productDetail.adapter.AdapterProductDetailsPacks
import com.pepsidrc.ui.productDetail.adapter.AdapterProductsImages
import com.pepsidrc.ui.search.SearchActivity
import com.pepsidrc.utils.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import org.jetbrains.anko.backgroundColor

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent

class ProductDetailActivity :
    BaseActivity<ProductsDetailViewModel>(ProductsDetailViewModel::class),
    AdapterViewClickListener<ImagesItem>, AdapterViewPacksClickListener<PacksItem> {
    override fun onClickPacksAdapterView(
        objectAtPosition: PacksItem,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_PACKS -> {

                let {
                    if (!itemMastersItem.inCart) {
                        var i = 0


                        while (i < itemMastersItem?.packs!!.size) {
                            itemMastersItem?.packs?.get(i)?.isSelected =
                                false
                            i++
                        }
                        itemMastersItem?.packs?.get(position)?.isSelected =
                            true
                        adapterPacks?.submitList(itemMastersItem?.packs)
                        adapterPacks?.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    override fun onClickAdapterView(objectAtPosition: ImagesItem, viewType: Int, position: Int) {

        this.let {
            ImageRequestManager.with(ivProductFull)
                .url(objectAtPosition?.avatar_url)
                .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .build()
        }
    }

    var preferenceManager : PreferenceManager?=null

    lateinit var itemMastersItem: ItemMastersItem
    var title: String = ""
    override fun layout(): Int = R.layout.activity_product_detail
    private var adapterProductsImages: AdapterProductsImages? = null
    private var adapterPacks: AdapterProductDetailsPacks? = null
    var isBuyNow: Boolean = false
    var packPos: Int=0

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager(this)

        intent?.run {
            itemMastersItem = this!!.getParcelableExtra(KEY_PRODUCT_DATA)!!

            title = getStringExtra(KEY_TITLE).toString()
        }
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val manager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rv_products_images.layoutManager = manager
        rv_packs_product_detail.layoutManager = manager1


        fl_left_img_container.visibility = View.VISIBLE
        fl_left_img_container.setOnClickListener {

            onBackPressed()
        }
        iv_search.setOnClickListener {

            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                SearchActivity.getIntent(this),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )

        }
        iv_cart.setOnClickListener {
            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                CartDeatilActivity.getIntent(this)
            )
        }
        rlAddToCart.setOnClickListener {

            if (NetworkUtil.isInternetAvailable(this)) {
                addProductToCart(itemMastersItem)
            }
        }
        rlBuyNow.setOnClickListener {

            val MorePg_Logout_link_event = AdjustEvent("mjn6qw")
            Adjust.trackEvent(MorePg_Logout_link_event)


            if (NetworkUtil.isInternetAvailable(this)) {
                isBuyNow = false

                if (itemMastersItem?.inCart) {
                    this.let { UiUtils.hideSoftKeyboard(it) }
                    startActivity(
                        CartDeatilActivity.getIntent(this)
                    )
                } else {
                    isBuyNow = true
                    addProductToCart(itemMastersItem)

                }
            }
        }
        subscribeLoading()
        subscribeUi()
    }


    fun addProductToCart(objectAtPosition: ItemMastersItem) {
   //     var i = 0
  //      var isPackSelected = false
        val ProductDetailPg_AddToCart_Btn_event = AdjustEvent("7cw84a")
        Adjust.trackEvent(ProductDetailPg_AddToCart_Btn_event)

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
       /* if (NetworkUtil.isInternetAvailable(this)) {

            while (i < objectAtPosition?.packs!!.size) {

                if (objectAtPosition?.packs!!.get(i)?.isSelected) {

                    packPos=i
                    val productList = listOf(
                        OrderItemsAttributesItem(
                            objectAtPosition.price.toString(),
                            1,
                            objectAtPosition?.packs!!.get(i)?.id,
                            objectAtPosition?.packs!!.get(i)?.count,
                            objectAtPosition.id
                        )
                    )

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


    override fun onResume() {
        super.onResume()
        if(preferenceManager?.getBooleanPreference( Config.SharedPreferences.PROPERTY_IS_CART)!!){
            ivCounter.visibility=View.VISIBLE
            ivCounter.setText(preferenceManager?.getStringPreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE,""))

        }
        else{
            ivCounter.visibility=View.INVISIBLE

        }
        tv_tool_title.text = title
        itemMastersItem=   StoreProducts.getInstance().getProduct(itemMastersItem?.id)!!
        if(itemMastersItem==null){
            intent?.run {
                itemMastersItem = this!!.getParcelableExtra(KEY_PRODUCT_DATA)!!
            }
        }
        setData(itemMastersItem)

    }

    private fun subscribeUi() {
        model.addProductModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })

    }

    private fun showData(data: AddProductResponsePayload?) {
        //  adapterShopProducts?.submitList(data?.data?.itemMasters)
//        showSnackbar(data?.errors?.get(0)?.fieldLabel + " " + data?.errors?.get(0)?.detail, false)
        itemMastersItem.inCart=true
        itemMastersItem?.packs?.get(packPos)?.inCart=true
        preferenceManager?.savePreference(
            Config.SharedPreferences.PROPERTY_IS_CART,
            true
        )
        preferenceManager?.savePreference(
            Config.SharedPreferences.PROPERTY_IS_CART_VALUE,
            data?.data?.order?.cart_item_count
        )
        ivCounter.visibility=View.VISIBLE
        ivCounter.setText(data?.data?.order?.cart_item_count)
        StoreProducts.getInstance().addProduct(itemMastersItem)
        if (data!!.success) {
            showSnackbar(data?.message, false)
            tvAddToCart.text = AndroidUtils.getString(R.string.added_to_cart)
            rlAddToCart.backgroundColor = R.color.grey
            rlAddToCart.isEnabled = false
            if (isBuyNow) {
                startActivity(
                    CartDeatilActivity.getIntent(this)
                )
            }
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

    fun setData(item: ItemMastersItem) {
        item?.images?.let {
            if (it.size > 0) {
                ImageRequestManager.with(ivProductFull)
                    .url(it.get(0)?.avatar_url)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()
            } else {
                ImageRequestManager.with(ivProductFull)
                    .setPlaceholderImage(R.drawable.no_image_icon)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()
            }
        }
        tv_product_name?.text = item?.name
        tv_product_desc?.text = item?.itemdescription
        tv_price?.text = item.price.toString()
        if (item.inCart) {
            tvAddToCart.text = AndroidUtils.getString(R.string.added_to_cart)
            rlAddToCart.backgroundColor = R.color.grey
            rlAddToCart.isEnabled = false
        }
        this.let {
            adapterProductsImages = AdapterProductsImages(this, it)
            rv_products_images.adapter = adapterProductsImages
            item.images?.let {
                if (it.size > 0) {
                    adapterProductsImages?.submitList(item.images)
                    adapterProductsImages?.notifyDataSetChanged()
                } else {
                    val image = listOf(
                        ImagesItem(
                            null, 0
                        )
                    )
                    adapterProductsImages?.submitList(image)
                    adapterProductsImages?.notifyDataSetChanged()
                }
            }

      /*      adapterPacks = AdapterProductDetailsPacks(this, this)
            rv_packs_product_detail.adapter = adapterPacks
            adapterPacks?.submitList(item.packs)
            adapterPacks?.notifyDataSetChanged()*/
        }
    }

    companion object {
        const val KEY_PRODUCT_DATA = "KEY_SUBCATEGORY_DATA"
        const val KEY_TITLE = "KEY_TITLE"

        fun getIntent(context: Context?, item: ItemMastersItem?, name: String?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, ProductDetailActivity::class.java).putExtra(
                KEY_PRODUCT_DATA,
                item
            ).putExtra(KEY_TITLE, name)

        }
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
}
