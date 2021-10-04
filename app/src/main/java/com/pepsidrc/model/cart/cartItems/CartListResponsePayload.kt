package com.pepsidrc.model.cart.cartItems

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta
import com.pepsidrc.model.signUp.ErrorsItem

data class CartListResponsePayload(@SerializedName("data")
                                   val data: Data,
                                   @SerializedName("success")
                                   val success: Boolean = false,
                                   @SerializedName("meta")
                                   val meta: Meta,
                                   @SerializedName("message")
                                   val message: String = "",
                                   @SerializedName("errors")
                                   val errors: List<ErrorsItem>?)