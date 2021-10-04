package com.pepsidrc.ui.navigation.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.orderlist.OrderListResponsePayload
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import com.pepsidrc.ui.navigation.ui.shop.ShopRepository
import com.pepsidrc.utils.Logger
import retrofit2.HttpException

class OrdersViewModel(
    private val orderListRepository: OrderListRepository,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val searchEvent = SingleLiveEvent<SearchEvent>()
    val orderListModel = MutableLiveData<OrderListResponsePayload>()


    fun getOrderList() {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            orderListRepository.getOrderList()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    orderListModel.value = it
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

                        orderListModel.value =
                            Gson().fromJson(error, OrderListResponsePayload::class.java)
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

}