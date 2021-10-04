package com.pepsidrc.ui.login

import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.login.User
import io.reactivex.Single

interface LoginRepository {
    fun loginResponse(data: LoginRequestPayload) : Single<LoginResponse>
    fun saveUserData(token: String?,data: User,isRemember :Boolean)

}