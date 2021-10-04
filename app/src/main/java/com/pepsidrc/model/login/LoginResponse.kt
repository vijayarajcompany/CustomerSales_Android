package com.pepsidrc.model.login

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta

data class LoginResponse(@SerializedName("data")
                         val data: LoginData,
                         @SerializedName("success")
                         val success: Boolean = false,
                         @SerializedName("meta")
                         val meta: Meta,
                         @SerializedName("message")
                         val message: String = "")