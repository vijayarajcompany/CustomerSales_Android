package com.pepsidrc.model.cart

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.cart.cartItems.AddressesItem

data class OrderDetails(@SerializedName("addresses")
                        val addresses: List<AddressesItem>?,
                        @SerializedName("total_amount")
                        val totalAmount: String? = null,
                        @SerializedName("order_item_amount")
                        val orderItemAmount: String? = null,
                        @SerializedName("cart_item_count")
                        val cart_item_count: String? = null,
                        @SerializedName("order_number")
                        val orderNumber: String? = null,
                        @SerializedName("extra_charges")
                        val extraCharges: String? = null,
                        @SerializedName("promotion_id")
                        val promotionId: String? = null,
                        @SerializedName("id")
                        val id: Int = 0,
                        @SerializedName("shipping_address")
                        val shippingAddress: String? = null,
                        @SerializedName("user")
                        val user: User,
                        @SerializedName("status")
                        val status: String = "",
                        @SerializedName("promotion")
                        val promotion: String? = null,
                        @SerializedName("order_items")
                        val orderItems: List<OrderItemsItem>?)