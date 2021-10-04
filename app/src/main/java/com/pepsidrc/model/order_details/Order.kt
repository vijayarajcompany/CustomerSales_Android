package com.pepsidrc.model.order_details

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.orderlist.User

data class Order(@SerializedName("address")
                 val address: Address,
                 @SerializedName("order_item_amount")
                 val orderItemAmount: String?,
                 @SerializedName("order_number")
                 val orderNumber: String?,
                 @SerializedName("extra_charges")
                 val extraCharges: String?,
                 @SerializedName("source_amount")
                 val sourceAmount: Double = 0.0,
                 @SerializedName("created_at")
                 val createdAt: String?,
                 @SerializedName("promotion_id")
                 val promotionId: String?,
                 @SerializedName("promotion_amount")
                 val promotionAmount:String?,
                 @SerializedName("order_date")
                 val orderDate: String?,
                 @SerializedName("order_payment_type")
                 val orderPaymentType: String?,
                 @SerializedName("shipping_charge")
                 val shippingCharge: String?,
                 @SerializedName("updated_at")
                 val updatedAt: String?,
                 @SerializedName("total_amount")
                 val totalAmount: Double = 0.0,
                 @SerializedName("id")
                 val id: Int = 0,
                 @SerializedName("shipping_address")
                 val shippingAddress: String?,
                 @SerializedName("vat_charge")
                 val vatCharge: Double = 0.0,
                 @SerializedName("user")
                 val user: User,
                 @SerializedName("cart_item_count")
                 val cartItemCount: Int = 0,
                 @SerializedName("status")
                 val status: String?,
                 @SerializedName("order_items")
                 val orderItems: List<OrderItemsItem>?,
                 @SerializedName("promotion")
                 val promotion: String?)