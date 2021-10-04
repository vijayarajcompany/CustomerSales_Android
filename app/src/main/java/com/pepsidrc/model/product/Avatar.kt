package com.pepsidrc.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Avatar(@SerializedName("url")
                  var url: String?): Parcelable