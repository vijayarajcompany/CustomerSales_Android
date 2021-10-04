package com.pepsidrc.model.order_details

import com.google.gson.annotations.SerializedName

data class ProfilePicture(@SerializedName("thumb")
                          val thumb: Thumb,
                          @SerializedName("url")
                          val url: String?)