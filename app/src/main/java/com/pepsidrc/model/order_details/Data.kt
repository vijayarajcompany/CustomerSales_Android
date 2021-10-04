package com.pepsidrc.model.order_details

import com.google.gson.annotations.SerializedName

data class Data(@SerializedName("order")
                val order: Order)