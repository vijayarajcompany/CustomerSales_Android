package com.pepsidrc.model.profile

import com.google.gson.annotations.SerializedName

data class ProfilePicture(@SerializedName("url")
                          val url: String? = null)