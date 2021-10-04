package com.pepsidrc.ui.products


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.brand.BrandListResponse
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.UpdateProductRequestPayload
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class ProductRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : ProductRepository {
    override fun getBrandList(): Single<BrandListResponse> {
        return restApi.getBrandList(pre.getBearerToken())
    }

    override fun getProductsFilter(
        subcat_id: Int,
        category_id: List<Int?>,
        brand_id: List<Int?>,
        min_price: Int,
        max_price: Int,
        sort_by: String,
        column : String,
        per: Int,
        page: Int
    ): Single<ProductResponsePayload> {
        return restApi.getProductsFilter(
            pre.getBearerToken(),
            subcat_id,
            category_id,
            brand_id,
            min_price,
            max_price,
            sort_by,
            column,
            per,
            page
        )
    }

    override fun updateProduct(addProductRequestPayload: UpdateProductRequestPayload): Single<AddProductResponsePayload> {
        return restApi.updateProduct(pre.getBearerToken(), addProductRequestPayload)
    }

    override fun addProduct(addProductRequestPayload: AddProductRequestPayload): Single<AddProductResponsePayload> {
        return restApi.addProduct(pre.getBearerToken(), addProductRequestPayload)
    }

    override fun getProducts(subcat_id: Int): Single<ProductResponsePayload> {
        return restApi.getProducts(pre.getBearerToken(), subcat_id)
    }
}