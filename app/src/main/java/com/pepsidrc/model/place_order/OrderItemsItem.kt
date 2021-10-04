package com.pepsidrc.model.place_order

import com.google.gson.annotations.SerializedName

data class OrderItemsItem(@SerializedName("amount")
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