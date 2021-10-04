package com.pepsidrc.model.brand

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta
import com.pepsidrc.model.signUp.ErrorsItem

data class BrandListResponse(@SerializedName("data")
                             val data: BrandData,
                             @SerializedName("success")
                             val success: Boolean = false,
                             @SerializedName("meta")
                             val meta: Meta,
                             @SerializedName("message")
                             val message: String?,
                             @SerializedName("errors")
                             var errors: List<ErrorsItem>?)