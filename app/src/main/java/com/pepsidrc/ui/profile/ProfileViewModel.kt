package com.pepsidrc.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.updatePassword.UpdatePasswordRequestPayload
import com.pepsidrc.model.profile.UserDetailResponse
import com.pepsidrc.model.updatePassword.UserPasswordRequest
import com.pepsidrc.utils.Logger
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val scheduler: SchedulerProvider
) :
    AbstractViewModel() {
    val userDetailResponse = MutableLiveData<UserDetailResponse>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val userUpdateResponse = MutableLiveData<UserDetailResponse>()
    val changepasswordResponse = MutableLiveData<UserDetailResponse>()

    private var mediaTypeTextPlain = "text/plain"


    fun changePassword(current_password: String, new_password: String) {
        searchEvent.value = SearchEvent(isLoading = true)

        val user = UserPasswordRequest(current_password, new_password)
        val payLoadRequest = UpdatePasswordRequestPayload(user)
        launch {
            profileRepository.updatePassword(payLoadRequest)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    changepasswordResponse.value = it
                    searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)

                }, {

                    try {
                        Logger.Debug(msg = it.toString())
                        val error = it as HttpException
                        val errorBody = error?.response()?.errorBody()?.run {

                            val r = string()
                            Logger.Debug(msg = r)
                            val error = r.replaceRange(0, 0, "")
                                .replaceRange(r.length, r.length, "")
                            //  val json = Gson().toJson(error)

                            changepasswordResponse.value =
                                Gson().fromJson(error, UserDetailResponse::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

    fun fetchUser() {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            profileRepository.getUserDetail()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    userDetailResponse.value = it
                    searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)

                }, {

                    try{
                    Logger.Debug(msg = it.toString())
                    val error = it as HttpException
                    val errorBody = error?.response()?.errorBody()?.run {

                        val r = string()
                        Logger.Debug(msg = r)
                        val error = r.replaceRange(0, 0, "")
                            .replaceRange(r.length, r.length, "")
                        userDetailResponse.value =
                            Gson().fromJson(error, UserDetailResponse::class.java)
                        searchEvent.value =
                            SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                    }
                    }
                    catch (e :Exception){
                        e.printStackTrace()
                    }
                })
        }
    }


    fun updateUser(fName: String, lName: String, email: String, selectedImageUriPath: String) {
        val fName = RequestBody.create(MediaType.parse(mediaTypeTextPlain), fName)
        val lName = RequestBody.create(MediaType.parse(mediaTypeTextPlain), lName)
        val email = RequestBody.create(MediaType.parse(mediaTypeTextPlain), email)
        var photo: MultipartBody.Part? = null

        if (selectedImageUriPath != null) {


            try {
                val imageUri = Uri.parse(selectedImageUriPath)
                val file = File(imageUri.path)
                val fileName = file.name

                val contentType = MediaType.parse("image/*")

                val requestFile = RequestBody.create(
                    contentType,
                    file
                )

                photo = MultipartBody.Part.createFormData(
                    "user[profile_picture]",
                    fileName,
                    requestFile
                )


            } catch (e: Exception) {
            }

        }


        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            profileRepository.updateProfile(fName, lName, email, photo)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    userUpdateResponse.value = it
                    searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)

                }, {

                    try{
                    Logger.Debug(msg = it.toString())
                    val error = it as HttpException
                    val errorBody = error?.response()?.errorBody()?.run {

                        val r = string()
                        Logger.Debug(msg = r)
                        val error = r.replaceRange(0, 0, "")
                            .replaceRange(r.length, r.length, "")
                        userUpdateResponse.value =
                            Gson().fromJson(error, UserDetailResponse::class.java)
                        searchEvent.value =
                            SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                    }
                    }
                    catch (e :Exception){
                        e.printStackTrace()
                    }
                })
        }
    }

}