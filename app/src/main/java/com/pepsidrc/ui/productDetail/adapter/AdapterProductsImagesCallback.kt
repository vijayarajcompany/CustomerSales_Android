package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.product.ImagesItem
import com.pepsidrc.model.product.ItemMastersItem


class AdapterProductsImagesCallback : DiffUtil.ItemCallback<ImagesItem>() {

    override fun areItemsTheSame(oldItem: ImagesItem, newItem: ImagesItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ImagesItem, newItem: ImagesItem): Boolean {
        return oldItem == newItem
    }
}