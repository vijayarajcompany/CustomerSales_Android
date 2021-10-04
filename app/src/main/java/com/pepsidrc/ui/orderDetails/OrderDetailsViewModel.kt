package com.pepsidrc.ui.orderDetails

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.cancelOrder.OrderCancelResponsePayLoad
import com.pepsidrc.model.order_details.OrderDetailsResponse
import com.pepsidrc.ui.products.ProductRepository
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.TAG
import retrofit2.HttpException

class OrderDetailsViewModel(
    private val orderDetailRepository: OrderDetailRepository,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {

    val searchEvent = SingleLiveEvent<SearchEvent>()
    val cancelOrderModel = MutableLiveData<OrderCancelResponsePayLoad>()
    val orderDetailsModel = MutableLiveData<OrderDetailsResponse>()

    fun cancelOrder(order_id: Int) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {

            orderDetailRepository.cancelOrder(order_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({

                    Logger.Debug(msg = it.toString())
                    cancelOrderModel.value = it
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
                        cancelOrderModel.value =
                            Gson().fromJson(error, OrderCancelResponsePayLoad::class.java)
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

    fun orderDetails(order_id: Int) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {

            orderDetailRepository.orderDetails(order_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({

                    Logger.Debug(msg = it.toString())
                    orderDetailsModel.value = it
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
                            orderDetailsModel.value =
                                Gson().fromJson(error, OrderDetailsResponse::class.java)
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
