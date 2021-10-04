package com.pepsidrc.ui.coupons


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.cart.cartItems.CartListResponsePayload
import com.pepsidrc.model.cart.placeorder.OrderRequestPayload
import com.pepsidrc.model.coupons.ApplyCouponResponsePayload
import com.pepsidrc.model.coupons.CouponsListResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.place_order.OrderPlaceResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class CouponRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : CouponRepository {
    override fun applyCoupon(promoCode: String?, orderId: Int): Single<ApplyCouponResponsePayload> {
        return restApi.applyCoupon(pre.getBearerToken(),promoCode,orderId)
    }

    override fun getCouponList(): Single<CouponsListResponsePayload> {
       return restApi.getCouponList(pre.getBearerToken())
    }

}