package com.pepsidrc.model.coupons

import com.google.gson.annotations.SerializedName

data class PromotionsItem(@SerializedName("promo_no")
                          val promoNo: String?,
                          @SerializedName("discount_amount")
                          val discountAmount: String?,
                          @SerializedName("id")
                          val id: Int = 0,
                          @SerializedName("promodescription")
                          val promodescription: String?,
                          @SerializedName("promo_customers")
                          val promoCustomers: List<PromoCustomersItem>?,
                          @SerializedName("promoitems")
                          val promoitems: List<PromoitemsItem>?,
                          @SerializedName("expirey_date")
                          val expireyDate: String?)