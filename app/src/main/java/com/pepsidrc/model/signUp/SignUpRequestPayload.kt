package com.pepsidrc.model.signUp

import com.google.gson.annotations.SerializedName

data class SignUpRequestPayload(@SerializedName("registration")
                                val registration: Registration)