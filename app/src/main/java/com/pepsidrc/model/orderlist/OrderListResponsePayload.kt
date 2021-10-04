package com.pepsidrc.model.orderlist

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta
import com.pepsidrc.model.signUp.ErrorsItem

data class OrderListResponsePayload(@SerializedName("data")
                                    var data: OrderListData,
                                    @SerializedName("success")
                                    var success: Boolean = false,
                                    @SerializedName("meta")
                                    var meta: Meta,
                                    @SerializedName("message")
                                    var message: String?,
                                    @SerializedName("errors")
                                    var errors: List<ErrorsItem>?)