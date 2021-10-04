package com.pepsidrc.model.coupons

import com.google.gson.annotations.SerializedName

data class PromoCustomersItem(@SerializedName("updated_at")
                              val updatedAt: String = "",
                              @SerializedName("created_at")
                              val createdAt: String = "",
                              @SerializedName("promotion_id")
                              val promotionId: Int = 0,
                              @SerializedName("id")
                              val id: Int = 0,
                              @SerializedName("customer_id")
                              val customerId: Int = 0)