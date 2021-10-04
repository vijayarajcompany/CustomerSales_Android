package com.pepsidrc.model.coupons

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta

data class CouponsListResponsePayload(@SerializedName("data")
                                      val data: CouponListData,
                                      @SerializedName("success")
                                      val success: Boolean = false,
                                      @SerializedName("meta")
                                      val meta: Meta)