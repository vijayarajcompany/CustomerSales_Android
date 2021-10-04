package com.pepsidrc.model.cart.cancelOrder

import com.google.gson.annotations.SerializedName

data class CancelOrderData(@SerializedName("order")
                val order: Order)