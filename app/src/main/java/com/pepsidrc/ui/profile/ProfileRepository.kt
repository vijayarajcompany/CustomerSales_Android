package com.pepsidrc.ui.profile

import com.pepsidrc.model.updatePassword.UpdatePasswordRequestPayload
import com.pepsidrc.model.profile.UserDetailResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProfileRepository {
    fun getUserDetail() : Single<UserDetailResponse>
    fun deleteUser() : Single<UserDetailResponse>
    fun updatePassword(data: UpdatePasswordRequestPayload) : Single<UserDetailResponse>
    fun updateProfile(fNameRequestBody: RequestBody,
                         lNameRequestBody: RequestBody,
                         emailRequestBody: RequestBody,
                         imageRequestBody: MultipartBody.Part?) : Single<UserDetailResponse>

}