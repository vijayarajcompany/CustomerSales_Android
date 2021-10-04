package com.pepsidrc.ui.login

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.login.*
import com.pepsidrc.utils.Logger
import retrofit2.HttpException
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent


class LoginViewModel(private val loginRepository: LoginRepository, private val scheduler: SchedulerProvider) :
    AbstractViewModel() {
    val loginData = MutableLiveData<LoginResponse>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val loginDataError = MutableLiveData<LoginResponseError>()

    fun login(email: String, password: String) {
        searchEvent.value = SearchEvent(isLoading = true)

        val user = UserRequest(password,null,
             email)
        val payLoadSignIn = LoginRequestPayload(user)
        launch {
            loginRepository.loginResponse(payLoadSignIn)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    loginData.value = it

                    val fname = it.data.response.user.firstName
                    val lname = it.data.response.user.lastName

                    val SignIn_Success_event = AdjustEvent("kjg3y2")
                    SignIn_Success_event.addCallbackParameter("Signin_Email", user.email);
                    SignIn_Success_event.addCallbackParameter("Signin_FirstName",fname);
                    SignIn_Success_event.addCallbackParameter("Signin_LastName", lname);
                    SignIn_Success_event.addCallbackParameter("Signin_ErnNumber", user.ern_number);

                    Adjust.trackEvent(SignIn_Success_event)

                    searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)


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

                            loginDataError.value =
                                Gson().fromJson(error, LoginResponseError::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }
                    }
                    catch (e :Exception){
                        e.printStackTrace()
                    }
                   // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

    fun loginWithERNNumber(ernNumer: String,password: String) {
        searchEvent.value = SearchEvent(isLoading = true)

        val user = UserRequest(password,
            ernNumer,null)
        val payLoadSignIn = LoginRequestPayload(user)
        launch {
            loginRepository.loginResponse(payLoadSignIn)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    loginData.value = it
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

                        loginDataError.value = Gson().fromJson(error, LoginResponseError::class.java)
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
   public fun saveUserDetail(token:String?,user: User,isRemember :Boolean) {

        loginRepository.saveUserData(token,user,isRemember)


    }
}