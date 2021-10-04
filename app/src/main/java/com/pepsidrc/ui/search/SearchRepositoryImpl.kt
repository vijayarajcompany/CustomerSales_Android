package com.pepsidrc.ui.search


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class SearchRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : SearchRepository {
    override fun searchProduct(query: String): Single<ProductResponsePayload> {
        return restApi.searchProduct(pre.getBearerToken(),query)
    }


}