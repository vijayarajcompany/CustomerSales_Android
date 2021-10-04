package com.pepsidrc.ui.login


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.login.User
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class LoginRepositoryImpl(private val restApi: AppRestApiFast,private val pre: PreferenceManager) : LoginRepository {
    override fun saveUserData(token: String?,data: User,isRemember :Boolean) {
        pre.loginUser(token,data,isRemember)
    }


    override fun loginResponse(data: LoginRequestPayload): Single<LoginResponse> {
        return restApi.login(data)
    }






}