package com.pepsidrc.model.shipping

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.signUp.ErrorsItem

data class ShippingResponsePayload(@SerializedName("data")
                                   val data: ShippingData,
                                   @SerializedName("success")
                                   val success: Boolean = false,
                                   @SerializedName("message")
                                   val message: String = "",
                                   @SerializedName("errors")
                                   val errors: List<ErrorsItem>?)