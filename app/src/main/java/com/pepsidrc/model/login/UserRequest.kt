package com.pepsidrc.model.login

data class UserRequest(val password: String? = "",
                       val ern_number: String? = "",
                       val email: String? = "")