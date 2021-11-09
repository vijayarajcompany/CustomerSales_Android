package com.pepsidrc.ui.signup

import androidx.lifecycle.MutableLiveData
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.signUp.Registration
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.signUp.SignUpResponseError
import com.pepsidrc.utils.Logger
import retrofit2.HttpException

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

class SignUpViewModel(private val signUpRepository: SignUpRepository, private val scheduler: SchedulerProvider) :
    AbstractViewModel() {
    val signUpModel = MutableLiveData<SignUpResponse>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val signUpModelError = MutableLiveData<SignUpResponseError>()

    fun signUp(email: String, password: String,passwordConfirmation: String,
               ernNumber: String,name: String) {
        searchEvent.value = SearchEvent(isLoading = true)

        val user = Registration(password,passwordConfirmation,"","",
            ernNumber,name,email)
        val payLoadRegister = SignUpRequestPayload(user)
        launch {
            signUpRepository.signUp(payLoadRegister)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    signUpModel.value = it


//                    val current = LocalDateTime.now()
//                    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//                    val formattedDate = current.format(formatter)
//
//                    var formatter2 = DateTimeFormatter.ofPattern("hh:mm:ss")
//                    val formattedDate2 = current.format(formatter2)

                    val SignUp_Success_event = AdjustEvent("9ghat9")
                    SignUp_Success_event.setCallbackId("SignUp_Success_event");
                    SignUp_Success_event.addCallbackParameter("NewUser_Name", name);
                    SignUp_Success_event.addCallbackParameter("NewUser_Email", email);
                    Adjust.trackEvent(SignUp_Success_event)

                    searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)

                }, {
                    try{
                    Logger.Debug(msg = it.toString())
                    val error = it as HttpException
                    val errorBody = error?.response()?.errorBody()?.run {
                        val r=string()
                        Logger.Debug(msg = r)
                        val error=r.replaceRange(0,0,"")
                            .replaceRange(r.length,r.length,"")
                        //  val json = Gson().toJson(error)

                        signUpModelError.value = Gson().fromJson(error, SignUpResponseError::class.java)
                        searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                    }
                    }
                    catch (e :Exception){
                        e.printStackTrace()
                    }

                    // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

}