package com.pepsidrc.model.login

import com.google.gson.annotations.SerializedName

data class LoginData(@SerializedName("response")
                val response: Response,
                     @SerializedName("invited_feature")
                val invitedFeature: String = "")