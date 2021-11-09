package com.pepsidrc.ui.navigation.ui.home

import androidx.lifecycle.MutableLiveData
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.division.DivisionListingResponse
import com.pepsidrc.model.division.DivisionRequest
import com.pepsidrc.model.division.DivisionUpdateRequestPayload
import com.pepsidrc.model.home.banner.BannerResponsePayload
import com.pepsidrc.model.profile.UserDetailResponse
import com.pepsidrc.model.signUp.Registration
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.utils.Logger
import retrofit2.HttpException

class HomeViewModel(
    private val categoryRepository: HomeRepository,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val categoryModel = MutableLiveData<CategoriesResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val bannerModel = MutableLiveData<BannerResponsePayload>()
    val divisionModel = MutableLiveData<DivisionListingResponse>()
    val updatedivisionModel = MutableLiveData<UserDetailResponse>()


    fun getCategories() {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            categoryRepository.getCategories()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    categoryModel.value = it
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

                            categoryModel.value =
                                Gson().fromJson(error, CategoriesResponsePayload::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }


    fun getBanners() {
        launch {
            categoryRepository.getBanners()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    bannerModel.value = it


                }, {

                    try{
                    Logger.Debug(msg = it.toString())
                    val error = it as HttpException
                    val errorBody = error?.response()?.errorBody()?.run {

                        val r = string()
                        Logger.Debug(msg = r)
                        val error = r.replaceRange(0, 0, "")
                            .replaceRange(r.length, r.length, "")

                        bannerModel.value =
                            Gson().fromJson(error, BannerResponsePayload::class.java)


                    }
                    }
                    catch (e :Exception){
                        e.printStackTrace()
                    }
                })


        }
    }


    fun getDivision() {
        launch {
            categoryRepository.getDivision()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    divisionModel.value = it


                }, {
                    try{
                    Logger.Debug(msg = it.toString())
                    val error = it as HttpException
                    val errorBody = error?.response()?.errorBody()?.run {

                        val r = string()
                        Logger.Debug(msg = r)
                        val error = r.replaceRange(0, 0, "")
                            .replaceRange(r.length, r.length, "")

                        divisionModel.value =
                            Gson().fromJson(error, DivisionListingResponse::class.java)


                    }
                    }
                    catch (e :Exception){
                        e.printStackTrace()
                    }
                })


        }
    }

    fun updateDivision(division: String?) {
        launch {
            val user = DivisionRequest(division)
            val payLoadDivisionUpdate = DivisionUpdateRequestPayload(user)
            categoryRepository.updateDivision(payLoadDivisionUpdate)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    updatedivisionModel.value = it

                    val Division_event = AdjustEvent("ujegfy")
                    Division_event.setCallbackId("Division_event");
                    Division_event.addCallbackParameter("Division",  division);
                    Adjust.trackEvent(Division_event)

                }, {
                    try{
                    Logger.Debug(msg = it.toString())
                    val error = it as HttpException
                    val errorBody = error?.response()?.errorBody()?.run {

                        val r = string()
                        Logger.Debug(msg = r)
                        val error = r.replaceRange(0, 0, "")
                            .replaceRange(r.length, r.length, "")

                        updatedivisionModel.value =
                            Gson().fromJson(error, UserDetailResponse::class.java)


                    }
                    }
                    catch (e :Exception){
                        e.printStackTrace()
                    }
                })


        }
    }


}