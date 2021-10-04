package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pepsidrc.model.cart.cartItems.OrderItemsItem
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.product.ItemMastersItem


class AdapterCartListProductsCallback : DiffUtil.ItemCallback<OrderItemsItem>() {

    override fun areItemsTheSame(oldItem: OrderItemsItem, newItem: OrderItemsItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: OrderItemsItem, newItem: OrderItemsItem): Boolean {
        return oldItem == newItem
    }
}