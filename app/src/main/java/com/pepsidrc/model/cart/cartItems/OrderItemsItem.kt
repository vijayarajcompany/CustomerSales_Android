package com.pepsidrc.model.cart.cartItems

import com.google.gson.annotations.SerializedName

data class OrderItemsItem(@SerializedName("amount")
                          val amount: String?,
                          @SerializedName("total_amount")
                          val total_amount: String?,
                          @SerializedName("quantity")
                          var quantity: Int = 0,
                          @SerializedName("pack_size")
                          val packSize: Int = 0,
                          @SerializedName("id")
                          val id: Int = 0,
                          @SerializedName("pack")
                          val pack: Pack?,
                          @SerializedName("item_master")
                          val itemMaster: ItemMaster)