package com.pepsidrc.model.cart.cartItems

import com.google.gson.annotations.SerializedName

data class AddressesItem(@SerializedName("address")
                         val address: String = "",
                         @SerializedName("updated_at")
                         val updatedAt: String = "",
                         @SerializedName("addressable_id")
                         val addressableId: Int = 0,
                         @SerializedName("latitude")
                         val latitude: String?,
                         @SerializedName("addressable_type")
                         val addressableType: String = "",
                         @SerializedName("created_at")
                         val createdAt: String = "",
                         @SerializedName("id")
                         val id: Int = 0,
                         @SerializedName("title")
                         val title: String = "",
                         @SerializedName("longitude")
                         val longitude: String?)