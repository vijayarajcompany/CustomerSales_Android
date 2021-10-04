package com.pepsidrc.model.cart

import com.google.gson.annotations.SerializedName

data class ProfilePicture(@SerializedName("url")
                          val url: String? = null)