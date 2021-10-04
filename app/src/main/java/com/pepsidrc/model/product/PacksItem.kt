package com.pepsidrc.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PacksItem(@SerializedName("price")
                     var price: String?,
                     @SerializedName("in_cart?")
                     var inCart: Boolean = false,
                     @SerializedName("name")
                     var name: String?,
                     @SerializedName("count")
                     var count: Int = 0,
                     @SerializedName("isSelected")
                     var isSelected: Boolean = false,
                     @SerializedName("productPosition")
                     var productPosition: Int ,
                     @SerializedName("id")
                     var id: Int = 0): Parcelable