package com.pepsidrc.model.order_details

import com.google.gson.annotations.SerializedName

data class ImagesItem(@SerializedName("avatar_url")
                      val avatarUrl: String?,
                      @SerializedName("id")
                      val id: Int = 0)