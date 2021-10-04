package com.pepsidrc.ui.cart

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.address.AddAddressRequestPayload
import com.pepsidrc.address.AddressResponsePayload
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.UpdateProductRequestPayload
import com.pepsidrc.model.cart.cartItems.CartListResponsePayload
import com.pepsidrc.model.cart.placeorder.OrderRequest
import com.pepsidrc.model.cart.placeorder.OrderRequestPayload
import com.pepsidrc.model.place_order.OrderPlaceResponsePayload
import com.pepsidrc.model.shipping.ShippingResponsePayload
import com.pepsidrc.ui.products.ProductRepository
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.TAG
import retrofit2.HttpException

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val scheduler: SchedulerProvider
) :
    AbstractViewModel() {
    val cartViewModel = MutableLiveData<CartListResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val addProductModel = MutableLiveData<AddProductResponsePayload>()
    val placeOrderModel = MutableLiveData<OrderPlaceResponsePayload>()
    val addAddressModel = MutableLiveData<AddressResponsePayload>()
    val shippingModel = MutableLiveData<ShippingResponsePayload>()

    fun getCartList() {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            cartRepository.getCartList()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    cartViewModel.value = it
                    searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)

                }, {
                    try {
                        Logger.Debug(msg = it.toString())
                        val error = it as HttpException
                       if( error.code()==401){


                       }else {
                           val errorBody = error?.response()?.errorBody()?.run {

                               val r = string()
                               Logger.Debug(msg = r)
                               val error = r.replaceRange(0, 0, "")
                                   .replaceRange(r.length, r.length, "")
                               //  val json = Gson().toJson(error)

                               cartViewModel.value =
                                   Gson().fromJson(error, CartListResponsePayload::class.java)
                               searchEvent.value =
                                   SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                           }
                       }
                    }
                    catch (e: Exception){
                        e.printStackTrace()
                    }
                    // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

    fun updateProduct(addProductRequestPayload: UpdateProductRequestPayload) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {

            var f = Gson().toJson(addProductRequestPayload, UpdateProductRequestPayload::class.java)
            Logger.Debug(TAG, f)
            productRepository.updateProduct(addProductRequestPayload)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addProductModel.value = it
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
                        addProductModel.value =
                            Gson().fromJson(error, AddProductResponsePayload::class.java)
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
    fun updateAddress(addAddressRequestPayload: AddAddressRequestPayload) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {


            cartRepository.updateAddress(addAddressRequestPayload)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addAddressModel.value = it
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
                        addAddressModel.value =
                            Gson().fromJson(error, AddressResponsePayload::class.java)
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

    fun placeOrder(mode : String?) {

        val orderReq = OrderRequest(mode)
        val orderRequestPayload = OrderRequestPayload(orderReq)

        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            cartRepository.placeOrder(orderRequestPayload)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    placeOrderModel.value = it
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

                        placeOrderModel.value =
                            Gson().fromJson(error, OrderPlaceResponsePayload::class.java)
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

    fun getShippingCharge() {

        launch {
            cartRepository.getShippingData()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    shippingModel.value = it


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

                            shippingModel.value =
                                Gson().fromJson(error, ShippingResponsePayload::class.java)


                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

}