package com.pepsidrc.model.cart.cancelOrder

import com.google.gson.annotations.SerializedName

data class PacksItem(@SerializedName("in_cart?")
                     val inCart: String?,
                     @SerializedName("price")
                     val price: String?,
                     @SerializedName("name")
                     val name: String = "",
                     @SerializedName("count")
                     val count: Int = 0,
                     @SerializedName("id")
                     val id: Int = 0)