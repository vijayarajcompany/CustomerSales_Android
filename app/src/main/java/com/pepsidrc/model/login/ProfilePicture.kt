package com.pepsidrc.model.login

import com.google.gson.annotations.SerializedName

data class ProfilePicture(@SerializedName("url")
                          val url: String? = null)