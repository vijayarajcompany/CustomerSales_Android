package com.pepsidrc.model.cart

import com.google.gson.annotations.SerializedName

data class AddProductRequestPayload(@SerializedName("order")
                                    val order: Order)