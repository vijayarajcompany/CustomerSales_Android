package com.pepsidrc.ui.cart


import com.pepsidrc.address.AddAddressRequestPayload
import com.pepsidrc.address.AddressResponsePayload
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.UpdateProductRequestPayload
import com.pepsidrc.model.cart.cartItems.CartListResponsePayload
import com.pepsidrc.model.cart.placeorder.OrderRequestPayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.place_order.OrderPlaceResponsePayload
import com.pepsidrc.model.shipping.ShippingResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class CartRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : CartRepository {
    override fun getShippingData(): Single<ShippingResponsePayload> {
        return restApi.getShippingData(pre.getBearerToken())
    }

    override fun updateAddress(addProductRequestPayload: AddAddressRequestPayload): Single<AddressResponsePayload> {
        return restApi.updateAddress(pre.getBearerToken(),addProductRequestPayload)
    }


    override fun placeOrder(orderRequestPayload: OrderRequestPayload): Single<OrderPlaceResponsePayload> {
        return restApi.placeOrder(pre.getBearerToken(),orderRequestPayload)
    }

    override fun getCartList(): Single<CartListResponsePayload> {
        return restApi.getCartList(pre.getBearerToken())
    }
}