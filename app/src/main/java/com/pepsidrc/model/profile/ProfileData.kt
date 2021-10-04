package com.pepsidrc.model.profile

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.login.User

data class ProfileData(@SerializedName("user")
                val user: User
)