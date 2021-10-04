package com.pepsidrc.ui.coupons.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.BuildConfig

import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.coupons.PromotionsItem
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.orderlist.OrderItemsItem
import com.pepsidrc.model.orderlist.OrderListResponsePayload
import com.pepsidrc.model.orderlist.OrdersItem
import com.pepsidrc.ui.navigation.ui.home.adapter.*
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_category_home.view.*
import kotlinx.android.synthetic.main.item_category_home.view.imgSource
import kotlinx.android.synthetic.main.item_coupon.view.*
import kotlinx.android.synthetic.main.item_orders.view.*
import kotlinx.android.synthetic.main.item_orders_detail.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterCouponList(
    private val adapterViewClickListener: AdapterViewClickListener<PromotionsItem>?,
    val activity: Activity
) : ListAdapter<PromotionsItem, AdapterCouponList.ViewHolder>(
    AdapterCouponListCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): AdapterCouponList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_coupon, parent, false)

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),position,adapterViewClickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: PromotionsItem,position: Int,
                 adapterViewClick: AdapterViewClickListener<PromotionsItem>?) {

            itemView.txt_coupon_code?.text = allProducts?.promoNo
            itemView.txt_coupon_offer?.text = allProducts?.promodescription
            itemView.txt_expiry_date?.text = AndroidUtils.getDate(allProducts?.expireyDate)
            itemView.txt_apply.setOnClickListener {

                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_APPLY_COUPON, adapterPosition
                )
            }

        }
    }

}