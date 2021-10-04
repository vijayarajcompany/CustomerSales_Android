package com.pepsidrc.model.cart

import com.google.gson.annotations.SerializedName

data class UpdateProductRequestPayload(@SerializedName("order")
                                    val order: UpdateOrder)