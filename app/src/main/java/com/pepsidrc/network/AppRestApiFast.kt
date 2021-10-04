package com.pepsidrc.network


import com.pepsidrc.address.AddAddressRequestPayload
import com.pepsidrc.address.AddressResponsePayload
import com.pepsidrc.model.brand.BrandListResponse
import com.pepsidrc.model.cart.AddProductRequestPayload
import com.pepsidrc.model.cart.AddProductResponsePayload
import com.pepsidrc.model.cart.UpdateProductRequestPayload
import com.pepsidrc.model.cart.cancelOrder.OrderCancelResponsePayLoad
import com.pepsidrc.model.cart.cartItems.CartListResponsePayload
import com.pepsidrc.model.cart.placeorder.OrderRequestPayload
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.coupons.ApplyCouponResponsePayload
import com.pepsidrc.model.coupons.CouponsListResponsePayload
import com.pepsidrc.model.division.DivisionListingResponse
import com.pepsidrc.model.division.DivisionUpdateRequestPayload
import com.pepsidrc.model.home.banner.BannerResponsePayload
import com.pepsidrc.model.login.LoginRequestPayload
import com.pepsidrc.model.login.LoginResponse
import com.pepsidrc.model.order_details.OrderDetailsResponse
import com.pepsidrc.model.orderlist.OrderListResponsePayload
import com.pepsidrc.model.place_order.OrderPlaceResponsePayload
import com.pepsidrc.model.product.ProductResponsePayload
import com.pepsidrc.model.updatePassword.UpdatePasswordRequestPayload
import com.pepsidrc.model.profile.UserDetailResponse
import com.pepsidrc.model.reset.ResetPasswordRequestPayload
import com.pepsidrc.model.reset.ResetPasswordResponsePayload
import com.pepsidrc.model.shipping.ShippingResponsePayload
import com.pepsidrc.model.signUp.SignUpRequestPayload
import com.pepsidrc.model.signUp.SignUpResponse
import com.pepsidrc.model.subcategories.SubcategoriesResponsePayload
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import com.pepsidrc.utils.Config
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface AppRestApiFast {

    @POST(Config.Endpoints.LOGIN_API)
    fun login(@Body data: LoginRequestPayload)
            : Single<LoginResponse>

    // reset password

    @POST(Config.Endpoints.RESET_PASSWORD_API)
    fun resetPassword(@Body data: ResetPasswordRequestPayload)
            : Single<ResetPasswordResponsePayload>
    /*Authentication*/

    /* Register */
    @POST(Config.Endpoints.SIGN_UP_API)
    fun signUp(@Body data: SignUpRequestPayload)
            : Single<SignUpResponse>

    /*   @POST(Config.Endpoints.LOGIN_API)
       fun signUp(
           @Body data: LoginRequestPayload
       ): Observable<Response<AuthenticationResponse>>

       @POST(Config.Endpoints.LOGIN_API)
       fun getProducts(
           @Header("Authorization")  token :String,
           @Query("uuid")  uuid: String?,
           @Query("with_subcategory")  with_subcategory:String
       ): Observable<Response<ProductsWithCategoryResponse>>
   */

    //fetch user
    @GET(Config.Endpoints.FETCH_USER_API)
    fun fetchUser(@Header("Authorization") token: String)
            : Single<UserDetailResponse>

    //SearchProduct
    @GET(Config.Endpoints.SEARCH_PRODUCT_API)
    fun searchProduct(@Header("Authorization") token: String,
                      @Query("search")search: String)
            : Single<ProductResponsePayload>

    //delete user
    @GET(Config.Endpoints.DELETE_USER_API)
    fun deleteUser(@Header("Authorization") token: String)
            : Single<UserDetailResponse>

    //change password
    @PATCH(Config.Endpoints.CHANGE_PASSWORD_API)
    fun updatePassword(@Header("Authorization") token: String, @Body data: UpdatePasswordRequestPayload)
            : Single<UserDetailResponse>


    //categories
    @GET(Config.Endpoints.CATEGORIES_API)
    fun getCategories(@Header("Authorization") token: String)
            : Single<CategoriesResponsePayload>

    //sub categories
/*    @GET(Config.Endpoints.SUB_CATEGORIES_LIST_API)
    fun getSubCategories(
        @Query("category_id") cat_id: Int?,
        @Query("trade_offer_id") offer_id: Int?
    ): Single<SubcategoriesResponsePayload>*/

    @GET(Config.Endpoints.SUB_CATEGORIES_LIST_API)
    fun getSubCategories(
        @Query("category_id") cat_id: Int?
    ): Single<SubcategoriesResponsePayload>


    //brandList

    @GET(Config.Endpoints.BRAND_LIST_API)
    fun getBrandList(
        @Header("Authorization" ) token: String
    ): Single<BrandListResponse>

    //trade_offers
    @GET(Config.Endpoints.TRADE_OFFER_LIST_API)
    fun getTradeOffers(@Header("Authorization" ) token: String)
            : Single<TradeOfferResponsePayload>

    //Products List
    @GET(Config.Endpoints.PRODUCT_LIST_API)
    fun getProducts(@Header("Authorization" ) token: String,@Query("subcategory_id") subcat_id: Int)
            : Single<ProductResponsePayload>

    @GET(Config.Endpoints.PRODUCT_LIST_API)
    fun getProductsFilter(@Header("Authorization" ) token: String,
                          @Query("subcategory_id") subcat_id: Int,
                          @Query("category_id")category_id: List<Int?>,
                          @Query("brand_id")brand_id: List<Int?>,
                          @Query("min_price")min_price: Int,
                          @Query("max_price")max_price: Int,
                          @Query("sort_by")sort_by: String,
                          @Query("column")column: String,
                          @Query("per")per: Int,
                          @Query("page")page: Int)
            : Single<ProductResponsePayload>

    //Banner PlaceOrderData
    @GET(Config.Endpoints.BANNER_DATA_API)
    fun getBanners()
            : Single<BannerResponsePayload>

    @GET(Config.Endpoints.DIVISION_DATA_API)
    fun getDivisions(@Header("Authorization" ) token: String)
            : Single<DivisionListingResponse>


    @PATCH(Config.Endpoints.UPDATE_USER_PROFILE)
    fun updateDivisions(@Header("Authorization" ) token: String,@Body data: DivisionUpdateRequestPayload)
            : Single<UserDetailResponse>


    @PATCH(Config.Endpoints.ADD_PRODUCT_API)
    fun addProduct(@Header("Authorization" ) token: String,@Body data: AddProductRequestPayload)
            : Single<AddProductResponsePayload>

    @PATCH(Config.Endpoints.ADD_PRODUCT_API)
    fun updateProduct(@Header("Authorization" ) token: String,@Body data: UpdateProductRequestPayload)
            : Single<AddProductResponsePayload>


    @GET(Config.Endpoints.CART_LIST)
    fun getCartList(@Header("Authorization" ) token: String)
            : Single<CartListResponsePayload>



    @GET(Config.Endpoints.COUPON_LIST)
    fun getCouponList(@Header("Authorization" ) token: String)
            : Single<CouponsListResponsePayload>

    @GET(Config.Endpoints.APPLY_COUPON)
    fun applyCoupon(@Header("Authorization" ) token: String,
                    @Query("promo_no") promo_no: String?,
                    @Query("order_id") order_id: Int)
            : Single<ApplyCouponResponsePayload>

    @PATCH(Config.Endpoints.PLACE_ORDER_API)
    fun placeOrder(@Header("Authorization" ) token: String,
                   @Body data: OrderRequestPayload
    )
            : Single<OrderPlaceResponsePayload>


    @PATCH(Config.Endpoints.UPDATE_ADDRESS_API)
    fun updateAddress(@Header("Authorization" ) token: String,
                   @Body data: AddAddressRequestPayload
    )
            : Single<AddressResponsePayload>

    @GET(Config.Endpoints.SHIPPING_CHARGE_API)
    fun getShippingData(@Header("Authorization" ) token: String)
            : Single<ShippingResponsePayload>

    @PATCH(Config.Endpoints.CANCEL_ORDER_API)
    fun cancelOrder(@Header("Authorization" ) token: String,
                    @Query("order_id") order_id: Int)
            : Single<OrderCancelResponsePayLoad>

    @GET(Config.Endpoints.ORDER_DETAILS_API)
    fun orderDetails(@Header("Authorization" ) token: String,
                    @Path("id") order_id: Int)
            : Single<OrderDetailsResponse>

    @GET(Config.Endpoints.ORDER_LIST_API)
    fun orderList(@Header("Authorization" ) token: String)
            : Single<OrderListResponsePayload>

    @Multipart
    @PATCH(Config.Endpoints.UPDATE_USER_PROFILE)
    fun updateUserProfile(
        @Header("Authorization") token: String
        , @Part("user[first_name]") first_name: RequestBody
        , @Part("user[last_name]") last_name: RequestBody
        , @Part file: MultipartBody.Part?
    ): Single<UserDetailResponse>

}

