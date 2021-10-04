package com.pepsidrc.model.orderlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressesItem(@SerializedName("address")
                         var address: String?,
                         @SerializedName("updated_at")
                         var updatedAt: String?,
                         @SerializedName("addressable_id")
                         var addressableId: Int = 0,
                         @SerializedName("latitude")
                         var latitude: String?,
                         @SerializedName("addressable_type")
                         var addressableType: String?,
                         @SerializedName("created_at")
                         var createdAt: String?,
                         @SerializedName("id")
                         var id: Int = 0,
                         @SerializedName("title")
                         var title: String?,
                         @SerializedName("longitude")
                         var longitude: String?): Parcelable