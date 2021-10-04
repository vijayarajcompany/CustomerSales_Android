package com.pepsidrc.ui.resetPassword


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.UpdateProductRequestPayload
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.model.reset.ResetPasswordRequestPayload
import com.pepsidrc.model.reset.ResetPasswordResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class ResetPasswordRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : ResetPasswordRepository {
    override fun resetPassword(data: ResetPasswordRequestPayload): Single<ResetPasswordResponsePayload> {
    return  restApi.resetPassword(data)
    }

}