package com.pepsidrc.model.signUp

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta

data class SignUpResponseError(
    @SerializedName("success")
                               val success: Boolean = false,
                               @SerializedName("meta")
                               val meta: Meta,
                               @SerializedName("message")
                               val message: String = "",
                               @SerializedName("errors")
                               val errors: List<ErrorsItem>?)