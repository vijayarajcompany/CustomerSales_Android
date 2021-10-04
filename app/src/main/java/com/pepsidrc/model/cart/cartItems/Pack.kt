package com.pepsidrc.model.cart.cartItems

import com.google.gson.annotations.SerializedName

data class Pack(@SerializedName("price")
                val price: String?,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("count")
                val count: Int = 0,
                @SerializedName("id")
                val id: Int = 0)