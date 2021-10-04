package com.pepsidrc.ui.navigation.ui.orders

import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.orderlist.OrderListResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.subcategories.SubcategoriesResponsePayload
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import io.reactivex.Single

interface OrderListRepository {
    fun getOrderList() : Single<OrderListResponsePayload>

}