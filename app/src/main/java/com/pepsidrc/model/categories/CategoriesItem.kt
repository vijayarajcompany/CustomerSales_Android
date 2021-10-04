package com.pepsidrc.model.categories

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoriesItem(@SerializedName("image")
                          val image: Image?,
                          @SerializedName("name")
                          val name: String?,
                          @SerializedName("id")
                          val id: Int?,
                          @SerializedName("isChecked")
                          val isChecked: Boolean=false): Parcelable