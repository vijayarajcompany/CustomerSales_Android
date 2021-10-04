package com.pepsidrc.model.orderlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderItemsItem(@SerializedName("amount")
                          var amount: String?,
                          @SerializedName("quantity")
                          var quantity: Int = 0,
                          @SerializedName("pack_size")
                          var packSize: Int = 0,
                          @SerializedName("id")
                          var id: Int = 0,
                          @SerializedName("pack")
                          var pack: Pack?,
                          @SerializedName("item_master")
                          var itemMaster: ItemMaster?): Parcelable