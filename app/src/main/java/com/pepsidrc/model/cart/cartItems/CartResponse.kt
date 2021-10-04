package com.pepsidrc.model.cart.cartItems

import com.google.gson.annotations.SerializedName

data class CartResponse(@SerializedName("order")
                var order: Order)