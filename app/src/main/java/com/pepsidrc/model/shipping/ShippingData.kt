package com.pepsidrc.model.shipping

import com.google.gson.annotations.SerializedName

data class ShippingData(@SerializedName("setting")
                val setting: Setting)