package com.pepsidrc.ui.navigation.ui.shop

import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.subcategories.SubcategoriesResponsePayload
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import io.reactivex.Single

interface ShopRepository {
    fun getTradeOffer() : Single<TradeOfferResponsePayload>
    fun getCategories() : Single<CategoriesResponsePayload>

}