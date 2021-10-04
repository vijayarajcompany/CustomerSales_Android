package com.pepsidrc.model.cart.cancelOrder

import com.google.gson.annotations.SerializedName

data class OrderItemsItem(@SerializedName("order_item_type")
                          val orderItemType: String = "",
                          @SerializedName("amount")
                          val amount: String?,
                          @SerializedName("quantity")
                          val quantity: Int = 0,
                          @SerializedName("pack_size")
                          val packSize: Int = 0,
                          @SerializedName("id")
                          val id: Int = 0,
                          @SerializedName("pack")
                          val pack: Pack?,
                          @SerializedName("item_master")
                          val itemMaster: ItemMaster)