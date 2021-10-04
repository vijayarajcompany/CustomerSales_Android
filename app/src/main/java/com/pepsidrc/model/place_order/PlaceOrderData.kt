package com.pepsidrc.model.place_order

import com.google.gson.annotations.SerializedName

data class PlaceOrderData(@SerializedName("order")
                val order: PlaceOrder)