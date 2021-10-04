package com.pepsidrc.model.orderlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrdersItem(@SerializedName("address")
                      val address: Address?,
                      @SerializedName("addresses")
                      var addresses: List<AddressesItem>?,
                      @SerializedName("order_item_amount")
                      var orderItemAmount: String?,
                      @SerializedName("order_number")
                      var orderNumber: String = "",
                      @SerializedName("extra_charges")
                      var extraCharges: String?,
                      @SerializedName("source_amount")
                      var sourceAmount: String?,
                      @SerializedName("order_payment_type")
                      var order_payment_type: String?,
                      @SerializedName("created_at")
                      var created_at: String?,
                      @SerializedName("promotion_id")
                      var promotionId: String?,
                      @SerializedName("promotion_amount")
                      var promotionAmount: String?,
                      @SerializedName("total_amount")
                      var totalAmount: String?,
                      @SerializedName("id")
                      var id: Int = 0,
                      @SerializedName("shipping_address")
                      var shippingAddress: String?,
                      @SerializedName("order_date")
                      var orderDate: String?,
                      @SerializedName("user")
                      var user: User,
                      @SerializedName("isVisible")
                      var isVisible: Boolean=false,
                      @SerializedName("status")
                      var status: String = "",
                      @SerializedName("order_items")
                      var orderItems: List<OrderItemsItem>?,
                      @SerializedName("promotion")
                      var promotion: String?): Parcelable