package com.pepsidrc.model.cart

import com.google.gson.annotations.SerializedName

data class UpdateOrder(@SerializedName("order_items_attributes")
                 val orderItemsAttributes: List<UpdateOrderItemsAttributesItem>?)