package com.pepsidrc.model.cart

import com.google.gson.annotations.SerializedName

data class OrderItemsItem(@SerializedName("amount")
                          val amount: String?,
                          @SerializedName("quantity")
                          val quantity: Int = 0,
                          @SerializedName("updated_at")
                          val updatedAt: String = "",
                          @SerializedName("pack_size")
                          val packSize: Int = 0,
                          @SerializedName("created_at")
                          val createdAt: String = "",
                          @SerializedName("item_master_id")
                          val itemMasterId: Int = 0,
                          @SerializedName("id")
                          val id: Int = 0,
                          @SerializedName("order_id")
                          val orderId: Int = 0)