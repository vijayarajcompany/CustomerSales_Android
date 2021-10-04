package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.product.ItemMastersItem


class AdapterShopProductsCallback : DiffUtil.ItemCallback<ItemMastersItem>() {

    override fun areItemsTheSame(oldItem: ItemMastersItem, newItem: ItemMastersItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ItemMastersItem, newItem: ItemMastersItem): Boolean {
        return oldItem == newItem
    }
}