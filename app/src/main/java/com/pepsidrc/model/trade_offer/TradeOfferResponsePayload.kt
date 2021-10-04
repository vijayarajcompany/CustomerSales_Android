package com.pepsidrc.model.trade_offer

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta

data class TradeOfferResponsePayload(@SerializedName("data")
                                     val data: OfferData,
                                     @SerializedName("success")
                                     val success: Boolean = false,
                                     @SerializedName("meta")
                                     val meta: Meta,
                                     @SerializedName("message")
                                     val message: Int = 0)