package com.pepsidrc.model.subcategories

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.product.PacksItem
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubcategoriesItem(@SerializedName("image")
                             val image: Image?,
                             @SerializedName("quantity")
                             val quantity: String?,
                             @SerializedName("name")
                             val name: String = "",
                             @SerializedName("id")
                             val id: Int = 0,
                             @SerializedName("packs")
                             val packs: List<PacksItem>?)
                             : Parcelable