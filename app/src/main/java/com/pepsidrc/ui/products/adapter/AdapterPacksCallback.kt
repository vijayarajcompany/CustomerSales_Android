package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.product.ItemMastersItem
import com.pepsidrc.model.product.PacksItem


class AdapterPacksCallback : DiffUtil.ItemCallback<PacksItem>() {

    override fun areItemsTheSame(oldItem: PacksItem, newItem: PacksItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: PacksItem, newItem: PacksItem): Boolean {
        return oldItem == newItem
    }
}