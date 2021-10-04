package com.pepsidrc.model.product

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta
import com.pepsidrc.model.signUp.ErrorsItem

data class ProductResponsePayload(@SerializedName("data")
                                  var data: ProductData,
                                  @SerializedName("success")
                                  var success: Boolean = false,
                                  @SerializedName("message")
                                  var message: String?,
                                  @SerializedName("meta")
                                  val meta: Meta,
                                  @SerializedName("errors")
                                  var errors: List<ErrorsItem>?)