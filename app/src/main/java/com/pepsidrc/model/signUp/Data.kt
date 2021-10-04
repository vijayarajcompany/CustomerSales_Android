package com.pepsidrc.model.signUp

import com.google.gson.annotations.SerializedName

data class Data(@SerializedName("registration")
                val registration: RegistrationRes)