package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.subcategories.SubcategoriesItem


class AdapterCategoryCallback : DiffUtil.ItemCallback<CategoriesItem>() {

    override fun areItemsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem): Boolean {
        return oldItem == newItem
    }
}