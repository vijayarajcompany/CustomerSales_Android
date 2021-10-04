package com.pepsidrc.ui.subcategories.adapter

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
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterShopProductsCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterSubcategoriesPacksCallback
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_pack.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.backgroundResource

class AdapterSubcategoriesPacks(
    val activity: Activity
) : ListAdapter<PacksItem, AdapterSubcategoriesPacks.ViewHolder>(
    AdapterSubcategoriesPacksCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_pack, parent, false)
        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            product: PacksItem,
            position: Int
        ) {
          /*  if(product.isSelected){
                itemView.tvPack.backgroundResource=R.drawable.circular_back_black
            }*/
            itemView.tvPack?.text = product?.count?.toString()


        }
    }

}