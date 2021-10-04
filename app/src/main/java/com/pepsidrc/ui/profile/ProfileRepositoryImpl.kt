package com.pepsidrc.ui.profile


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.updatePassword.UpdatePasswordRequestPayload
import com.pepsidrc.model.profile.UserDetailResponse
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : ProfileRepository {
    override fun updateProfile(
        fNameRequestBody: RequestBody,
        lNameRequestBody: RequestBody,
        emailRequestBody: RequestBody,
        imageRequestBody: MultipartBody.Part?
    ): Single<UserDetailResponse> {
        return restApi.updateUserProfile(pre.getBearerToken(),fNameRequestBody,lNameRequestBody,imageRequestBody)
    }

    override fun updatePassword(data: UpdatePasswordRequestPayload): Single<UserDetailResponse> {
        return restApi.updatePassword(pre.getBearerToken(),data)
    }



    override fun deleteUser(): Single<UserDetailResponse> {
        return restApi.deleteUser(pre.getBearerToken())
    }

    override fun getUserDetail(): Single<UserDetailResponse> {
        return restApi.fetchUser(pre.getBearerToken())
    }

}