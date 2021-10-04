package com.pepsidrc.model.brand

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BrandItem(
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int
): Parcelable