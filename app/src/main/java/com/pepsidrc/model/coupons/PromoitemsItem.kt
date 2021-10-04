package com.pepsidrc.model.coupons

import com.google.gson.annotations.SerializedName

data class PromoitemsItem(@SerializedName("sale_uom")
                          val saleUom: String = "",
                          @SerializedName("add_3")
                          val add1: String?,
                          @SerializedName("add_2")
                          val add2: String?,
                          @SerializedName("foc_qty")
                          val focQty: String?,
                          @SerializedName("updated_at")
                          val updatedAt: String?,
                          @SerializedName("sale_qty")
                          val saleQty: String?,
                          @SerializedName("created_at")
                          val createdAt: String?,
                          @SerializedName("promotion_id")
                          val promotionId: Int = 0,
                          @SerializedName("id")
                          val id: Int = 0,
                          @SerializedName("customer_master_id")
                          val customerMasterId: String?,
                          @SerializedName("sales_item")
                          val salesItem: String?,
                          @SerializedName("foc_item")
                          val focItem: String?)