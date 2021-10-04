package com.pepsidrc.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImagesItem(@SerializedName("avatar_url")
                      var avatar_url: String?,
                      @SerializedName("id")
                      var id: Int = 0): Parcelable