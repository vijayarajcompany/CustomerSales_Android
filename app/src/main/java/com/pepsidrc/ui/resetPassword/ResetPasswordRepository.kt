package com.pepsidrc.ui.resetPassword

import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.reset.ResetPasswordRequestPayload
import com.pepsidrc.model.reset.ResetPasswordResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import io.reactivex.Single

interface ResetPasswordRepository {
    fun resetPassword(data: ResetPasswordRequestPayload) : Single<ResetPasswordResponsePayload>

}