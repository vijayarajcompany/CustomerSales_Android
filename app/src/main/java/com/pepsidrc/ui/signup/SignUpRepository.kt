package com.pepsidrc.ui.signup

import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import io.reactivex.Single

interface SignUpRepository {
    fun signUp(data: SignUpRequestPayload) : Single<SignUpResponse>

}