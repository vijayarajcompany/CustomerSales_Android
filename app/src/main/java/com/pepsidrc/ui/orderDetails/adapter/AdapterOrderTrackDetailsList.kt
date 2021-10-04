package com.pepsidrc.ui.orderDetails.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ScalingUtils
import com.pepsidrc.BuildConfig

import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.orderlist.OrderItemsItem
import com.pepsidrc.model.orderlist.OrderListResponsePayload
import com.pepsidrc.model.orderlist.OrdersItem
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterCategoryCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterOrderDetailsListCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterOrderListCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterShopProductsCallback
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_category_home.view.*
import kotlinx.android.synthetic.main.item_category_home.view.imgSource
import kotlinx.android.synthetic.main.item_orders.view.*
import kotlinx.android.synthetic.main.item_orders_detail.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterOrderTrackDetailsList(
    val activity: Activity
) : ListAdapter<OrderItemsItem, AdapterOrderTrackDetailsList.ViewHolder>(
    AdapterOrderDetailsListCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): AdapterOrderTrackDetailsList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_orders_detail, parent, false)

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: OrderItemsItem) {

            itemView.text_order_name_value?.text = allProducts?.itemMaster?.hierarchydesc
            itemView.text_order_quantity_value?.text = allProducts?.quantity?.toString()
            itemView.text_order_pack_size_value?.text = allProducts?.packSize?.toString()
            itemView.text_price_value?.text =
                AndroidUtils.getString(R.string.price_type) + "\n" + allProducts?.itemMaster?.price?.toString()

            allProducts?.itemMaster?.images?.let {

                if (it.size > 0) {
                    ImageRequestManager.with(itemView.imgSource)
                        .url(allProducts?.itemMaster?.images?.get(0)?.avatar_url)
                        .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .build()
                } else {
                    ImageRequestManager.with(itemView.ivProduct)
                        .setPlaceholderImage(R.drawable.no_image_icon)
                        .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .build()
                }
            }


        }
    }

}