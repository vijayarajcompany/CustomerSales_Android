package com.pepsidrc.model.orderlist
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImagesItem( @SerializedName("id")
                       val id: Int = 0,
                       @SerializedName("avatar_url")
                       val avatar_url: String): Parcelable