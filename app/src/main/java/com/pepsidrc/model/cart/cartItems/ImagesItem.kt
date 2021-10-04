package com.pepsidrc.model.cart.cartItems

import com.google.gson.annotations.SerializedName

data class ImagesItem(
                      @SerializedName("id")
                      val id: Int = 0,
                      @SerializedName("avatar_url")
                      val avatar_url: String
                      )