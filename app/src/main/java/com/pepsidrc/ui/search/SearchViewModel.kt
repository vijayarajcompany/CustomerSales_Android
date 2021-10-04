package com.pepsidrc.ui.search

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.model.signUp.Registration
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.signUp.SignUpResponseError
import com.pepsidrc.utils.Logger
import retrofit2.HttpException

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val scheduler: SchedulerProvider
) :
    AbstractViewModel() {
    val productSearchModel = MutableLiveData<ProductResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()

    fun searchProduct(query: String) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            searchRepository.searchProduct(query)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productSearchModel.value = it
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

                            productSearchModel.value =
                                Gson().fromJson(error, ProductResponsePayload::class.java)
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

}