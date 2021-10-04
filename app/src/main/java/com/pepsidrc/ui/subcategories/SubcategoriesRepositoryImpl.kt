package com.pepsidrc.ui.subcategories


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.subcategories.SubcategoriesResponsePayload
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class SubcategoriesRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : SubcategoriesRepository {
    override fun getSubCategories(
        cat_id: Int?,
        offer_id: Int?
    ): Single<SubcategoriesResponsePayload> {
     //   return restApi.getSubCategories(cat_id,offer_id)
        return restApi.getSubCategories(cat_id)

    }

}