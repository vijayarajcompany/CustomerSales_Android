package com.pepsidrc.ui.signup


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class SignUpRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : SignUpRepository {
    override fun signUp(data: SignUpRequestPayload): Single<SignUpResponse> {
        return restApi.signUp(data)
    }
}