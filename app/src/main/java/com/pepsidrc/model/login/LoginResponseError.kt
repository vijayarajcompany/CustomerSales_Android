package com.pepsidrc.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponseError(@SerializedName("success")
                              val success: Boolean = false,
                              @SerializedName("message")
                              val message: String = "",
                              @SerializedName("errors")
                              val errors: String = "")