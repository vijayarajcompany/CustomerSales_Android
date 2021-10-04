package com.pepsidrc.model.place_order

import com.google.gson.annotations.SerializedName

data class ImagesItem(@SerializedName("updated_at")
                      val updatedAt: String = "",
                      @SerializedName("created_at")
                      val createdAt: String = "",
                      @SerializedName("id")
                      val id: Int = 0,
                      @SerializedName("avatar")
                      val avatar: Avatar,
                      @SerializedName("imageable_id")
                      val imageableId: Int = 0,
                      @SerializedName("imageable_type")
                      val imageableType: String = "")