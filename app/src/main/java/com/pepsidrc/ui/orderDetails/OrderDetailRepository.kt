package com.pepsidrc.ui.orderDetails

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
import io.reactivex.Single

interface OrderDetailRepository {
    fun cancelOrder(subcat_id: Int): Single<OrderCancelResponsePayLoad>
    fun orderDetails(order_id: Int): Single<OrderDetailsResponse>

}