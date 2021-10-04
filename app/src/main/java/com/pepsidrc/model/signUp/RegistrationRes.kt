package com.pepsidrc.model.signUp

import com.google.gson.annotations.SerializedName

data class RegistrationRes(@SerializedName("last_name")
                        val lastName: String = "",
                           @SerializedName("first_name")
                        val firstName: String = "",
                           @SerializedName("email")
                        val email: String = "",
                           @SerializedName("activated")
                        val activated: Boolean = false)