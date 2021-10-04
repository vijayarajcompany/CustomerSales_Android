package com.pepsidrc.model.cart

import com.google.gson.annotations.SerializedName

data class UpdateOrderItemsAttributesItem(
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("quantity")
    val quantity: Int = 0,
    @SerializedName("pack_id")
    val pack_id: Int = 0,
    @SerializedName("pack_size")
    val packSize: Int = 0,
    @SerializedName("item_master_id")
    val itemMasterId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("_destroy")
    val _destroy: Int = 0
)