package com.pepsidrc.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductData(@SerializedName("item_masters")
                var itemMasters: ArrayList<ItemMastersItem>?): Parcelable