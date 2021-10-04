package com.pepsidrc.ui.navigation.ui.home


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class CategoryRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : CategoryRepository {
    override fun getCategories(): Single<CategoriesResponsePayload> {
        return restApi.getCategories(pre.getBearerToken())
    }

}