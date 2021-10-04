package com.pepsidrc.model.order_details

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.orderlist.ItemMaster

data class OrderItemsItem(@SerializedName("order_item_type")
                          val orderItemType: String = "",
                          @SerializedName("amount")
                          val amount: Double = 0.0,
                          @SerializedName("quantity")
                          val quantity: Int = 0,
                          @SerializedName("pack_size")
                          val packSize: String?,
                          @SerializedName("total_amount")
                          val totalAmount: Double = 0.0,
                          @SerializedName("id")
                          val id: Int = 0,
                          @SerializedName("item_master")
                          val itemMaster: ItemMaster
)