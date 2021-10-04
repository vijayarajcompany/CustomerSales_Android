package com.pepsidrc.ui.navigation.ui.orders


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.orderlist.OrderListResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.subcategories.SubcategoriesResponsePayload
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class OrderListRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : OrderListRepository {
    override fun getOrderList(): Single<OrderListResponsePayload> {
        return restApi.orderList(pre.getBearerToken())
    }


}