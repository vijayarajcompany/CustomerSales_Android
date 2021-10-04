package com.pepsidrc.model.trade_offer

import com.google.gson.annotations.SerializedName

data class OfferData(@SerializedName("trade_offers")
                val tradeOffers: List<TradeOffersItem>?)