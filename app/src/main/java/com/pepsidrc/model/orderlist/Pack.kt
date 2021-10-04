package com.pepsidrc.model.orderlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pack(@SerializedName("price")
                var price: String?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("count")
                var count: Int = 0,
                @SerializedName("id")
                var id: Int = 0): Parcelable