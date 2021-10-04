package com.pepsidrc.model.categories

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(@SerializedName("updated_at")
                 val updatedAt: String?,
                 @SerializedName("created_at")
                 val createdAt: String?,
                 @SerializedName("id")
                 val id: Int?,
                 @SerializedName("avatar")
                 val avatar: Avatar,
                 @SerializedName("imageable_id")
                 val imageableId: Int?,
                 @SerializedName("imageable_type")
                 val imageableType: String?): Parcelable