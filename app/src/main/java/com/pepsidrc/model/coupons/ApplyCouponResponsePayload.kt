package com.pepsidrc.model.coupons

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.signUp.ErrorsItem

data class ApplyCouponResponsePayload(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    val message: Message,
    @SerializedName("errors")
    val errors: List<ErrorsItem>
)