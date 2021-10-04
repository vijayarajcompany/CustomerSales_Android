package com.pepsidrc.ui.resetPassword

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.reset.ResetPasswordErrorResponsePayload

import com.pepsidrc.model.reset.ResetPasswordRequestPayload
import com.pepsidrc.model.reset.ResetPasswordResponsePayload

import com.pepsidrc.utils.Logger
import retrofit2.HttpException

class ResetPasswordViewModel(
    private val resetPasswordRepository: ResetPasswordRepository,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {

    val searchEvent = SingleLiveEvent<SearchEvent>()
    val resetPasswordModel = MutableLiveData<ResetPasswordResponsePayload>()
    val resetPasswordErrorModel = MutableLiveData<ResetPasswordErrorResponsePayload>()


    fun resetPassword(email: String) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            val resetPasswordRequest = ResetPasswordRequestPayload(email)
            resetPasswordRepository.resetPassword(resetPasswordRequest)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({

                    Logger.Debug(msg = it.toString())
                    resetPasswordModel.value = it
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
                        resetPasswordErrorModel.value =
                            Gson().fromJson(error, ResetPasswordErrorResponsePayload::class.java)
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
