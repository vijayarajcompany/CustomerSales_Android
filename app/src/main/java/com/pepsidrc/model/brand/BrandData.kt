package com.pepsidrc.model.brand

import com.google.gson.annotations.SerializedName

data class BrandData(@SerializedName("brand")
                val brand: ArrayList<BrandItem>?)