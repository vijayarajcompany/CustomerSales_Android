package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.orderlist.OrdersItem
import com.pepsidrc.model.subcategories.SubcategoriesItem


class AdapterOrderListCallback : DiffUtil.ItemCallback<OrdersItem>() {

    override fun areItemsTheSame(oldItem: OrdersItem, newItem: OrdersItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: OrdersItem, newItem: OrdersItem): Boolean {
        return oldItem == newItem
    }
}