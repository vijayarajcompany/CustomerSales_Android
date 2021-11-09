package com.pepsidrc.ui.navigation.ui.shop.adapter

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
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterCategoryCallback
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_category_home.view.*

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent

class AdapterCategories(
    private val adapterViewClickListener: AdapterViewClickListener<CategoriesItem>?,
    val activity: Activity
) : ListAdapter<CategoriesItem, AdapterCategories.ViewHolder>(
    AdapterCategoryCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterCategories.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_category_shop, parent, false)

      /*  val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
*/

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),adapterViewClickListener)
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: CategoriesItem, adapterViewClick: AdapterViewClickListener<CategoriesItem>?) {

            itemView.txtName?.text = allProducts.name
            ImageRequestManager.with(itemView.imgSource)
                .url(allProducts.image?.avatar?.url)
                .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .build()
            itemView.setOnClickListener {
                val ShopPg_Category_Tapped_event = AdjustEvent("8gg879")
                ShopPg_Category_Tapped_event.setCallbackId("ShopPg_Category_Tapped_event");
                Adjust.trackEvent(ShopPg_Category_Tapped_event)

                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY, adapterPosition
                )
            }
        }
    }

}