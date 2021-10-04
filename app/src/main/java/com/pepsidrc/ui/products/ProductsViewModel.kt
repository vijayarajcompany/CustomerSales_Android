package com.pepsidrc.ui.products

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.brand.BrandListResponse
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.TAG
import retrofit2.HttpException

class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {

    val productModel = MutableLiveData<ProductResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val addProductModel = MutableLiveData<AddProductResponsePayload>()
    val brandModel = MutableLiveData<BrandListResponse>()

    fun getProducts(subcat_id: Int) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            productRepository.getProducts(subcat_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productModel.value = it
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
                        productModel.value =
                            Gson().fromJson(error, ProductResponsePayload::class.java)
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

    fun getBrands() {
      //  searchEvent.value = SearchEvent(isLoading = true)
        launch {
            productRepository.getBrandList()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    brandModel.value = it
              /*      searchEvent.value =
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
                        brandModel.value =
                            Gson().fromJson(error, BrandListResponse::class.java)
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

    fun getProductsFiters(
        subcat_id: Int,
        category_id: ArrayList<Int?>,
        brand_id: ArrayList<Int?>,
        min_price: Int,
        max_price: Int,
        sort_by: String,
        column : String,
        per: Int,
        page: Int
    ) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            productRepository.getProductsFilter(
                subcat_id,
                category_id,
                brand_id,
                min_price,
                max_price,
                sort_by,
                column,
                per,
                page
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productModel.value = it
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
                            productModel.value =
                                Gson().fromJson(error, ProductResponsePayload::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                })
        }
    }

    fun addProduct(addProductRequestPayload: AddProductRequestPayload) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {

            var f = Gson().toJson(addProductRequestPayload, AddProductRequestPayload::class.java)
            Logger.Debug(TAG, f)
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
