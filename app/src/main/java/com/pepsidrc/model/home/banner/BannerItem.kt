package com.pepsidrc.model.home.banner

import com.google.gson.annotations.SerializedName

data class BannerItem(@SerializedName("avatar_url")
                      val avatarUrl: String = "",
                      @SerializedName("id")
                      val id: Int = 0)