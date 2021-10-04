package com.pepsidrc.model.home.banner

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta

data class BannerResponsePayload(@SerializedName("data")
                                 val data: BannerData,
                                 @SerializedName("success")
                                 val success: Boolean = false,
                                 @SerializedName("meta")
                                 val meta: Meta,
                                 @SerializedName("message")
                                 val message: Int = 0)