package com.pepsidrc.model.trade_offer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TradeOffersItem(@SerializedName("tradeoffer_id")
                           val tradeofferId: String? = null,
                           @SerializedName("trade_offer_type")
                           val tradeOfferType: String = "",
                           @SerializedName("flex_grp")
                           val flexGrp: String = "",
                           @SerializedName("sales_qty")
                           val salesQty: String = "",
                           @SerializedName("title")
                           val title: String = "",
                           @SerializedName("startdate")
                           val startdate: String = "",
                           @SerializedName("flex_desc")
                           val flexDesc: String = "",
                           @SerializedName("qualif_desc")
                           val qualifDesc: String = "",
                           @SerializedName("enddate")
                           val enddate: String = "",
                           @SerializedName("qualif_id")
                           val qualifId: String? = null,
                           @SerializedName("foc_qty")
                           val focQty: String = "",
                           @SerializedName("id")
                           val id: Int = 0,
                           @SerializedName("tradeitem")
                           val tradeitem: String = "",
                           @SerializedName("status")
                           val status: String = ""): Parcelable