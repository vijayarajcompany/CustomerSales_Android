package com.pepsidrc.model.cart

import com.google.gson.annotations.SerializedName

data class OrderData(@SerializedName("order")
                     val order: OrderDetails)