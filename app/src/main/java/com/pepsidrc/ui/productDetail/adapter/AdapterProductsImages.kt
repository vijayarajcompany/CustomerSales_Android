package com.pepsidrc.ui.productDetail.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ScalingUtils

import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.product.ImagesItem
import com.pepsidrc.model.product.ItemMastersItem
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterCategoryCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterProductsImagesCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterShopProductsCallback
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_product_images.view.*

class AdapterProductsImages(
    private val adapterViewClickListener: AdapterViewClickListener<ImagesItem>?,
    val activity: Activity
) : ListAdapter<ImagesItem, AdapterProductsImages.ViewHolder>(
    AdapterProductsImagesCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_product_images, parent, false)


        return ViewHolder(itemView, activity)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position, adapterViewClickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            product: ImagesItem,
            position: Int,
            adapterViewClick: AdapterViewClickListener<ImagesItem>?
        ) {

            if(product?.avatar_url==null){
                ImageRequestManager.with(itemView.ivProductItem)
                    .setPlaceholderImage(R.drawable.no_image_icon)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()
            }
            else {
                ImageRequestManager.with(itemView.ivProductItem)
                    .url(product?.avatar_url)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()
            }
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_SUB_CATEGORY, adapterPosition
                )
            }
        }
    }

}