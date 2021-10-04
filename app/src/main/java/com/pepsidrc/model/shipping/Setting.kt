package com.pepsidrc.model.shipping

import com.google.gson.annotations.SerializedName

data class Setting(@SerializedName("shipping_charge")
                   val shippingCharge: Double=0.0,
                   @SerializedName("minimun_amount")
                   val minimunAmount: Double=0.0)