package com.pepsidrc.model.orderlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfilePicture(@SerializedName("url")
                          var url: String?): Parcelable