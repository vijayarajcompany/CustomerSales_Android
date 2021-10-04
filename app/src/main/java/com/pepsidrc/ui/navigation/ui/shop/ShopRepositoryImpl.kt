package com.pepsidrc.ui.navigation.ui.shop


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.subcategories.SubcategoriesResponsePayload
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class ShopRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : ShopRepository {
    override fun getCategories(): Single<CategoriesResponsePayload> {
        return restApi.getCategories(pre.getBearerToken())
    }

    override fun getTradeOffer(): Single<TradeOfferResponsePayload> {
        return restApi.getTradeOffers(pre.getBearerToken())
    }
}