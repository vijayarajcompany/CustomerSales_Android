package com.pepsidrc.ui.productDetail.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.callbacks.AdapterViewPacksClickListener
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.product.PacksItem
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterCategoryCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterPacksCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterProductDetailsPacksCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterShopProductsCallback
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_pack.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.backgroundResource

class AdapterProductDetailsPacks(
    private val adapterViewClickListener: AdapterViewPacksClickListener<PacksItem>?,
    val activity: Activity
) : ListAdapter<PacksItem, AdapterProductDetailsPacks.ViewHolder>(
    AdapterProductDetailsPacksCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_pack, parent, false)

        /*  val displayMetrics = DisplayMetrics()
          activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
          val width = displayMetrics.widthPixels

          itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
  */

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position, adapterViewClickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            product: PacksItem,
            position: Int,
            adapterViewClick: AdapterViewPacksClickListener<PacksItem>?
        ) {
            if(product.isSelected || product.inCart){
                itemView.tvPack.backgroundResource=R.drawable.circular_back
                itemView.isClickable=false
            }
            itemView.tvPack?.text = product?.count?.toString()
            itemView.tvPack?.setOnClickListener {

                adapterViewClick?.onClickPacksAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_PACKS, adapterPosition
                )
            }

        }
    }

}