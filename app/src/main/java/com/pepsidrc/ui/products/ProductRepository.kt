package com.pepsidrc.ui.products

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
import io.reactivex.Single

interface ProductRepository {
    fun getProducts(subcat_id: Int): Single<ProductResponsePayload>
    fun addProduct(addProductRequestPayload: AddProductRequestPayload): Single<AddProductResponsePayload>
    fun updateProduct(addProductRequestPayload: UpdateProductRequestPayload): Single<AddProductResponsePayload>
    fun getProductsFilter(
        subcat_id: Int,
        category_id: List<Int?>,
        brand_id: List<Int?>,
        min_price: Int,
        max_price: Int,
        sort_by: String,
        column : String,
        per: Int,
        page: Int
    ): Single<ProductResponsePayload>
    fun getBrandList(): Single<BrandListResponse>

}