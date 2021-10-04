package com.pepsidrc.model.login

import com.google.gson.annotations.SerializedName

data class Response(@SerializedName("user")
                    val user: User,
                    @SerializedName("token")
                    val token: String = "")