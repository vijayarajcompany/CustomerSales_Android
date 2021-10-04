package com.pepsidrc.ui.coupons

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.UpdateProductRequestPayload
import com.pepsidrc.model.cart.cartItems.CartListResponsePayload
import com.pepsidrc.model.cart.placeorder.OrderRequest
import com.pepsidrc.model.cart.placeorder.OrderRequestPayload
import com.pepsidrc.model.coupons.ApplyCouponResponsePayload
import com.pepsidrc.model.coupons.CouponsListResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.UserRequest
import com.pepsidrc.model.place_order.OrderPlaceResponsePayload
import com.pepsidrc.model.signUp.Registration
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.signUp.SignUpResponseError
import com.pepsidrc.ui.products.ProductRepository
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.TAG
import retrofit2.HttpException

class CouponViewModel(
    private val couponListRepository: CouponRepository,
    private val scheduler: SchedulerProvider
) :
    AbstractViewModel() {
    val couponListViewModel = MutableLiveData<CouponsListResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val applyCouponViewModel = MutableLiveData<ApplyCouponResponsePayload>()

    fun getCouponList() {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            couponListRepository.getCouponList()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    couponListViewModel.value = it
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

                        couponListViewModel.value =
                            Gson().fromJson(error, CouponsListResponsePayload::class.java)
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

    fun applyCoupon(promoCode: String?, orderId: Int) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            couponListRepository.applyCoupon(promoCode, orderId)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    applyCouponViewModel.value = it
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

                        applyCouponViewModel.value =
                            Gson().fromJson(error, ApplyCouponResponsePayload::class.java)
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