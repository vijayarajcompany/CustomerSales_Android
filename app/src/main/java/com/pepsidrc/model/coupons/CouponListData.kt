package com.pepsidrc.model.coupons

import com.google.gson.annotations.SerializedName

data class CouponListData(@SerializedName("promotions")
                val promotions: ArrayList<PromotionsItem>?)