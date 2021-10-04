package com.pepsidrc.model.place_order

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta
import com.pepsidrc.model.signUp.ErrorsItem

data class OrderPlaceResponsePayload(@SerializedName("data")
                                     val data: PlaceOrderData,
                                     @SerializedName("success")
                                     val success: Boolean = false,
                                     @SerializedName("meta")
                                     val meta: Meta,
                                     @SerializedName("message")
                                     val message: String = "",
                                     @SerializedName("errors")
                                     val errors: List<ErrorsItem>?)