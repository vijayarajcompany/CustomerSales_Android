package com.pepsidrc.model.signUp

import com.google.gson.annotations.SerializedName

data class Registration(@SerializedName("password")
                        val password: String = "",
                        @SerializedName("password_confirmation")
                        val passwordConfirmation: String = "",
                        @SerializedName("document")
                        val document: String = "",
                        @SerializedName("last_name")
                        val lastName: String = "",
                        @SerializedName("ern_number")
                        val ernNumber: String = "",
                        @SerializedName("first_name")
                        val firstName: String = "",
                        @SerializedName("email")
                        val email: String = "")