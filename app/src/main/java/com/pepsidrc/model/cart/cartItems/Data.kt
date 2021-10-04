package com.pepsidrc.model.cart.cartItems

import com.google.gson.annotations.SerializedName

data class Data(@SerializedName("order")
                val order: Order)