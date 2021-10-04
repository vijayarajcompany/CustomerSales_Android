package com.pepsidrc.model.orderlist

import com.google.gson.annotations.SerializedName

data class OrderListData(@SerializedName("orders")
                var orders: List<OrdersItem>?)