package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.subcategories.SubcategoriesItem


class AdapterSubCategoryCallback : DiffUtil.ItemCallback<SubcategoriesItem>() {

    override fun areItemsTheSame(oldItem: SubcategoriesItem, newItem: SubcategoriesItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: SubcategoriesItem, newItem: SubcategoriesItem): Boolean {
        return oldItem == newItem
    }
}