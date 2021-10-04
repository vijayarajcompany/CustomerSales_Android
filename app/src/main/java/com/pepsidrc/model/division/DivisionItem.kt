package com.pepsidrc.model.division

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DivisionItem(@SerializedName("name")
                        val name: String?,
                        @SerializedName("description")
                        val description: String?,
                        @SerializedName("id")
                        val id: Int = 0): Parcelable