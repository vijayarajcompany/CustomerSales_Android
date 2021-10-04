package com.pepsidrc.ui.navigation.ui.shop

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import com.pepsidrc.utils.Logger
import retrofit2.HttpException

class ShopViewModel(
    private val shopRepository: ShopRepository,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val categoryModel = MutableLiveData<CategoriesResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val offerModel = MutableLiveData<TradeOfferResponsePayload>()


    fun getCategories() {

        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            shopRepository.getCategories()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    categoryModel.value = it
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
                        //  val json = Gson().toJson(error)

                        categoryModel.value =
                            Gson().fromJson(error, CategoriesResponsePayload::class.java)
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


    fun getTradeOffer() {
       // searchEvent.value = SearchEvent(isLoading = true)
        launch {
            shopRepository.getTradeOffer()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    offerModel.value = it
                   /* searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)*/

                }, {

                    try{
                    Logger.Debug(msg = it.toString())
                    val error = it as HttpException
                    val errorBody = error?.response()?.errorBody()?.run {

                        val r = string()
                        Logger.Debug(msg = r)
                        val error = r.replaceRange(0, 0, "")
                            .replaceRange(r.length, r.length, "")
                        offerModel.value =
                            Gson().fromJson(error, TradeOfferResponsePayload::class.java)
                       /* searchEvent.value =
                            SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)*/

                    }
                    }
                    catch (e :Exception){
                        e.printStackTrace()
                    }
                })
        }
    }
}