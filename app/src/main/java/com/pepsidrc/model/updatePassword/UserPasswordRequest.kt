package com.pepsidrc.model.updatePassword

data class UserPasswordRequest(val current_password: String? = "",
                               val password: String? = ""
                              )