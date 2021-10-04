package com.pepsidrc.ui.cart

import com.pepsidrc.address.AddAddressRequestPayload
import com.pepsidrc.address.AddressResponsePayload
import com.pepsidrc.model.cart.UpdateProductRequestPayload
import com.pepsidrc.model.cart.cartItems.CartListResponsePayload
import com.pepsidrc.model.cart.placeorder.OrderRequestPayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.place_order.OrderPlaceResponsePayload
import com.pepsidrc.model.shipping.ShippingResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import io.reactivex.Single

interface CartRepository {
    fun getCartList() : Single<CartListResponsePayload>
    fun placeOrder(orderRequestPayload: OrderRequestPayload) : Single<OrderPlaceResponsePayload>
    fun updateAddress(addProductRequestPayload: AddAddressRequestPayload) : Single<AddressResponsePayload>
    fun getShippingData() : Single<ShippingResponsePayload>

}