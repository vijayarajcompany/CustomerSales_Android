package com.pepsidrc.utils

class Config {

    object AdapterClickViewTypes {


        const val CLICK_VIEW_CATEGORY = 99
        const val CLICK_VIEW_MORE = 100
        const val CLICK_VIEW_SUB_CATEGORY = 97
        const val CLICK_VIEW_PRODUCT = 96
        const val CLICK_ADD_TO_CART_PRODUCT = 95
        const val CLICK_VIEW_PLUS_PRODUCT = 1000
        const val CLICK_VIEW_MINUS_PRODUCT = 1001
        const val CLICK_VIEW_DELETE_PRODUCT = 1002
        const val CLICK_VIEW_QUANTITY_CHANGED = 1003
        const val CLICK_VIEW_ORDER = 1004
        const val CLICK_VIEW_MORE_DETAILS = 1005
        const val CLICK_VIEW_APPLY_COUPON = 1006
        const val CLICK_VIEW_PRODUCT_PACKS = 9
    }

    object ConstantsAnswer {
        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        const val FOUR = 4
    }

    object AdapterLayouts {
        const val HEADER = 1
        const val FOOTER = 2

    }

    object Constants {
        const val HEADER_AUTHORISATION = "Authorization"
        const val asc = "asc"
        const val desc = "desc"
        const val ztoa = "ztoa"
        const val atoz = "atoz"

        /* Distance is in meters */
        const val RADIUS_DEFAULT = 100 * 1000 // 100 km = 100 * 1000 meters

        const val RESPONSE_CODE_POLL_ALREADY_SKIPPED = 409
    }

    object Endpoints {
        /* BASE */
        const val BASE_PATH = "/api/v1/"

        /* AUTH RELATED */
        const val SIGN_UP_API = BASE_PATH + "users/sign_up"
        const val LOGIN_API = BASE_PATH + "users/authenticate"
        const val RESET_PASSWORD_API = BASE_PATH + "users/password_resets"

        const val FETCH_USER_API = BASE_PATH + "users/account_profile"
        const val DELETE_USER_API = BASE_PATH + "users/account_profile"
        const val CHANGE_PASSWORD_API = BASE_PATH + "users/update_password"
        const val CATEGORIES_API = BASE_PATH + "categories"
        const val PRODUCT_LIST_API = BASE_PATH + "item_masters/"
        const val SUB_CATEGORIES_LIST_API = BASE_PATH + "subcategories/"
        const val TRADE_OFFER_LIST_API = BASE_PATH + "trade_offers"
        const val BANNER_DATA_API = BASE_PATH + "banners"
        const val DIVISION_DATA_API = BASE_PATH + "divisions"

        const val ADD_PRODUCT_API = BASE_PATH + "shopping_carts/add_order_items"

        const val CART_LIST = BASE_PATH + "shopping_carts/show_current"
        const val PLACE_ORDER_API = BASE_PATH + "shopping_carts/place_order"
        const val CANCEL_ORDER_API = BASE_PATH + "shopping_carts/cancele_order"

        const val ORDER_DETAILS_API = BASE_PATH + "shopping_carts/{id}"

        const val UPDATE_ADDRESS_API = BASE_PATH + "shopping_carts/add_address"
        const val SHIPPING_CHARGE_API = BASE_PATH + "shopping_carts/setting"

        const val COUPON_LIST = BASE_PATH + "promotions"
        const val APPLY_COUPON = BASE_PATH + "shopping_carts/apply_promo"

        const val ORDER_LIST_API = BASE_PATH + "shopping_carts"
        const val BRAND_LIST_API = BASE_PATH + "brands"
        const val SEARCH_PRODUCT_API = BASE_PATH + "item_masters/search"


        const val UPDATE_USER_PROFILE = BASE_PATH + "users/update_profile"

    }

    object SharedPreferences {
        // shared preferences name
        const val PROPERTY_PREF = "PREFERENCE_DEFAULT"

        // keys
        // login related
        const val PROPERTY_LOGIN_PREF = "PROPERTY_LOGIN_PREF" // is user logged in
        const val PROPERTY_JWT_TOKEN = "PROPERTY_JWT_TOKEN" // auth token
        const val PROPERTY_USER_ID = "PROPERTY_USER_ID" // user id
        const val PROPERTY_USER_NAME = "PROPERTY_USER_NAME" // user name
        const val PROPERTY_USER_EMAIL = "PROPERTY_USER_EMAIL" // user email
        const val PROPERTY_USER_ERN_NUMBER = "PROPERTY_USER_ERN" // user email

        const val PROPERTY_USER_IMAGE = "PROPERTY_USER_IMAGE" // user image
        const val PROPERTY_USER_IMAGE_THUMB = "PROPERTY_USER_IMAGE_THUMB" // user image thumb
        const val PROPERTY_USER_EARNED_POINTS = "PROPERTY_USER_EARNED_POINTS" // user earned points
        const val PROPERTY_IS_ON_BOARDING_SEEN = "PROPERTY_IS_ON_BOARDING_SEEN" // user on boarding
        const val PROPERTY_USER_IS_FB_LOGIN = "PROPERTY_USER_IS_FB_LOGIN" // user on boarding
        const val PROPERTY_IS_CART = "PROPERTY_IS_CART" // is user logged in
        const val PROPERTY_IS_CART_VALUE = "PROPERTY_IS_CART_VALUE" // is user logged in

        const val PROPERTY_IS_DIVISION_VALUE = "PROPERTY_IS_DIVISION_VALUE" // is user logged in
        const val PROPERTY_IS_DIVISION_SHOW = "PROPERTY_IS_DIVISION_SHOW" // is user logged in

        // notification
        const val PROPERTY_FCM_REGISTRATION_TOKEN = "PROPERTY_FCM_REGISTRATION_TOKEN"

    }
}