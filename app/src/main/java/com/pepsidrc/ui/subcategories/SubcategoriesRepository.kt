package com.pepsidrc.ui.subcategories

import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.subcategories.SubcategoriesResponsePayload
import io.reactivex.Single

interface SubcategoriesRepository {
    fun getSubCategories(cat_id: Int?,offer_id: Int?) : Single<SubcategoriesResponsePayload>
}