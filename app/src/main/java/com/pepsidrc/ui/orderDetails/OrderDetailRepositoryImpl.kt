package com.pepsidrc.ui.orderDetails


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.brand.BrandListResponse
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.UpdateProductRequestPayload
import com.pepsidrc.model.cart.cancelOrder.OrderCancelResponsePayLoad
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.order_details.OrderDetailsResponse
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.network.AppRestApiFast
import com.pepsidrc.ui.orderDetails.OrderDetailRepository
import io.reactivex.Single

class OrderDetailRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : OrderDetailRepository {
    override fun orderDetails(order_id: Int): Single<OrderDetailsResponse> {
        return restApi.orderDetails(pre.getBearerToken(),order_id)
    }

    override fun cancelOrder(order_id: Int): Single<OrderCancelResponsePayLoad> {
       return restApi.cancelOrder(pre.getBearerToken(),order_id)
    }

}