package com.pepsidrc.ui.coupons

import com.pepsidrc.model.cart.cartItems.CartListResponsePayload
import com.pepsidrc.model.cart.placeorder.OrderRequestPayload
import com.pepsidrc.model.coupons.ApplyCouponResponsePayload
import com.pepsidrc.model.coupons.CouponsListResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.place_order.OrderPlaceResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import io.reactivex.Single

interface CouponRepository {
    fun getCouponList() : Single<CouponsListResponsePayload>
    fun applyCoupon(promoCode : String?, orderId : Int) : Single<ApplyCouponResponsePayload>

}