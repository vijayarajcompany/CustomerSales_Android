package com.pepsidrc.model.cart.cancelOrder

import com.google.gson.annotations.SerializedName

data class Order(@SerializedName("order_item_amount")
                 val orderItemAmount: String?,
                 @SerializedName("order_number")
                 val orderNumber: String?,
                 @SerializedName("extra_charges")
                 val extraCharges: String?,
                 @SerializedName("source_amount")
                 val sourceAmount: String? ,
                 @SerializedName("created_at")
                 val createdAt: String?,
                 @SerializedName("promotion_id")
                 val promotionId: String?,
                 @SerializedName("promotion_amount")
                 val promotionAmount: String?,
                 @SerializedName("order_payment_type")
                 val orderPaymentType: String?,
                 @SerializedName("updated_at")
                 val updatedAt: String?,
                 @SerializedName("total_amount")
                 val totalAmount: Int = 0,
                 @SerializedName("id")
                 val id: Int = 0,
                 @SerializedName("shipping_address")
                 val shippingAddress: String?,
                 @SerializedName("user")
                 val user: User,
                 @SerializedName("status")
                 val status: String?,
                 @SerializedName("order_items")
                 val orderItems: List<OrderItemsItem>?,
                 @SerializedName("promotion")
                 val promotion: String?)