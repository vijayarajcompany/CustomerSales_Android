package com.pepsidrc.ui.productDetail

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.ui.products.ProductRepository
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.TAG
import retrofit2.HttpException

class ProductsDetailViewModel(
    private val productRepository: ProductRepository,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {

    val searchEvent = SingleLiveEvent<SearchEvent>()
    val addProductModel = MutableLiveData<AddProductResponsePayload>()

    fun addProduct(addProductRequestPayload: AddProductRequestPayload) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {

            var f= Gson().toJson(addProductRequestPayload, AddProductRequestPayload::class.java)
            Logger.Debug(TAG,f)
            productRepository.addProduct(addProductRequestPayload)
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

}
