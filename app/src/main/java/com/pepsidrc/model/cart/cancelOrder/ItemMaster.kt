package com.pepsidrc.model.cart.cancelOrder

import com.google.gson.annotations.SerializedName

data class ItemMaster(@SerializedName("itemdescription")
                      val itemdescription: String = "",
                      @SerializedName("quantity")
                      val quantity: String?,
                      @SerializedName("in_cart?")
                      val inCart: String?,
                      @SerializedName("activeitem")
                      val activeitem: String = "",
                      @SerializedName("excise")
                      val excise: String = "",
                      @SerializedName("vat")
                      val vat: String?,
                      @SerializedName("packs")
                      val packs: List<PacksItem>?,
                      @SerializedName("division")
                      val division: String = "",
                      @SerializedName("hierarchydesc")
                      val hierarchydesc: String = "",
                      @SerializedName("itemcode")
                      val itemcode: String = "",
                      @SerializedName("price")
                      val price: String?,
                      @SerializedName("name")
                      val name: String = "",
                      @SerializedName("unitspercase")
                      val unitspercase: String?,
                      @SerializedName("id")
                      val id: Int = 0,
                      @SerializedName("distchannel")
                      val distchannel: String = "",
                      @SerializedName("producthierarchy")
                      val producthierarchy: String = "",
                      @SerializedName("itemgroup")
                      val itemgroup: String = "")