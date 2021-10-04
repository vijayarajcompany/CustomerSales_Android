package com.pepsidrc.model.home.banner

import com.google.gson.annotations.SerializedName

data class BannerData(@SerializedName("banner")
                val banner: List<BannerItem>?)