package com.pepsidrc.ui.products.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ScalingUtils

import com.pepsidrc.R
import com.pepsidrc.base.StoreProducts
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.callbacks.AdapterViewPacksClickListener
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.model.product.ItemMastersItem
import com.pepsidrc.model.product.PacksItem
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterShopProductsCallback
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_product.view.tv_product_name
import kotlinx.android.synthetic.main.item_sub_category.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundResource

class AdapterShopProducts(
    private val adapterViewClickListener: AdapterViewClickListener<ItemMastersItem>?,
    private val adapterPacksViewClickListener: AdapterViewPacksClickListener<PacksItem>?,
    val activity: Activity
) : ListAdapter<ItemMastersItem, AdapterShopProducts.ViewHolder>(
    AdapterShopProductsCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_product, parent, false)

        /*  val displayMetrics = DisplayMetrics()
          activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
          val width = displayMetrics.widthPixels

          itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
  */

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        var allProduct =
            StoreProducts.getInstance().getProduct(getItem(position).id)
        if (allProduct == null) {
            allProduct = getItem(position)
        }
        holder.bind(
            allProduct!!,
            position,
            adapterViewClickListener,
            adapterPacksViewClickListener
        )
    }



    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            product: ItemMastersItem,
            position: Int,
            adapterViewClick: AdapterViewClickListener<ItemMastersItem>?,
            adapterPacksViewClick: AdapterViewPacksClickListener<PacksItem>?
        ) {
            var adapter = AdapterPacks(adapterPacksViewClick, activity)

            itemView.tv_product_name?.text = product?.name
            itemView.tvPrice.text =
                AndroidUtils.getString(R.string.price_type) + " " + product.price

            product?.images?.let {
            if(it?.size>0) {


//                ImageRequestManager.with(itemView.ivProduct)
//                    .url(it?.get(0)?.avatar_url)
//                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
//                    .build()



                var imageName =  product?.name
                imageName = imageName?.replace( "%20" , "_");
                imageName = imageName?.replace( " " , "_");

                try {
                    val res: Class<*> = R.drawable::class.java
                    val field = res.getField(imageName?.toLowerCase())
                    val drawableId: Int = field.getInt(null)

                    ImageRequestManager.with(itemView.ivProduct)
                        .setPlaceholderImage(drawableId)
                        .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .build()
                }
                catch (e:Exception) {
                    ImageRequestManager.with(itemView.ivProduct)
                        .setPlaceholderImage(R.drawable.no_image_icon)
                        .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .build()
                }















            }
            else{
                ImageRequestManager.with(itemView.ivProduct)
                    .setPlaceholderImage(R.drawable.no_image_icon)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()
            }








            }
            var manager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL, false
            )

            itemView.rv_packs?.layoutManager = manager
/*
            itemView.rv_packs?.adapter = adapter
            for (i in 0..product?.packs!!.size-1) {
                product?.packs?.get(i)?.productPosition=position
            }
             //   product.packs.
            adapter?.submitList(product?.packs)
            adapter?.notifyDataSetChanged()*/
            if(product?.inCart){
                itemView.btnAddToCart.text=AndroidUtils.getString(R.string.added_to_cart)
                itemView.btnAddToCart.backgroundColor=R.color.grey
                itemView.btnAddToCart.isEnabled=false
            }
            else{
                itemView.btnAddToCart.text=AndroidUtils.getString(R.string.add_to_cart)
                itemView.btnAddToCart.backgroundResource=R.drawable.rounded_corners_app_pink_button_bg
                itemView.btnAddToCart.isEnabled=true
            }
            itemView.btnAddToCart.setOnClickListener {

                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_ADD_TO_CART_PRODUCT, adapterPosition
                )
            }

            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT, adapterPosition
                )
            }
        }
    }

}