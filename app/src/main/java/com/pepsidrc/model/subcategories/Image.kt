package com.pepsidrc.model.subcategories

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(@SerializedName("avatar_url")
                 val avatar_url: String = "",

                 @SerializedName("id")
                 val id: Int = 0): Parcelable