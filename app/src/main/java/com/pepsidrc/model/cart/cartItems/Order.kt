package com.pepsidrc.model.cart.cartItems

import com.google.gson.annotations.SerializedName

data class Order(@SerializedName("addresses")
                 val addresses: List<AddressesItem>?,
                 @SerializedName("order_item_amount")
                 val orderItemAmount: String?,
                 @SerializedName("order_number")
                 val orderNumber: String = "",
                 @SerializedName("extra_charges")
                 val extraCharges: String?,
                 @SerializedName("source_amount")
                 val sourceAmount: String?,
                 @SerializedName("promotion_id")
                 val promotionId: String?,
                 @SerializedName("promotion_amount")
                 val promotionAmount:String?,
                 @SerializedName("total_amount")
                 val totalAmount: String?,
                 @SerializedName("vat_charge")
                 val vatCharge: String?,
                 @SerializedName("id")
                 val id: Int = 0,
                 @SerializedName("shipping_address")
                 val shippingAddress: String?,
                 @SerializedName("user")
                 val user: User,
                 @SerializedName("status")
                 val status: String = "",
                 @SerializedName("order_items")
                 val orderItems: ArrayList<OrderItemsItem>?,
                 @SerializedName("promotion")
                 val promotion: String?)