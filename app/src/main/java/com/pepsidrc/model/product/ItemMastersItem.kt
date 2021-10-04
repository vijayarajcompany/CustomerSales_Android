package com.pepsidrc.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemMastersItem(
    @SerializedName("itemdescription")
    var itemdescription: String?,
    @SerializedName("images")
    var images: ArrayList<ImagesItem>?,
    @SerializedName("quantity")
    var quantity: String?,
    @SerializedName("in_cart?")
    var inCart: Boolean = false,
    @SerializedName("activeitem")
    var activeitem: String?,
    @SerializedName("excise")
    var excise: String?,
    @SerializedName("vat")
    var vat: String?,
    @SerializedName("packs")
    var packs: List<PacksItem>?,
    @SerializedName("division")
    var division: String?,
    @SerializedName("hierarchydesc")
    var hierarchydesc: String?,
    @SerializedName("itemcode")
    var itemcode: String?,
    @SerializedName("price")
    var price: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("unitspercase")
    var unitspercase: String?,
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("distchannel")
    var distchannel: String?,
    @SerializedName("producthierarchy")
    var producthierarchy: String?,
    @SerializedName("itemgroup")
    var itemgroup: String?
) : Parcelable