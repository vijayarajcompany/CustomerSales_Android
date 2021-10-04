package com.pepsidrc.model.cart

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("tradeoffer_id")
                val tradeofferId: String? = null,
                @SerializedName("paymentterms")
                val paymentterms: String? = null,
                @SerializedName("city")
                val city: String? = null,
                @SerializedName("creditlimitamount")
                val creditlimitamount: String? = null,
                @SerializedName("document")
                val document: Document,
                @SerializedName("excise")
                val excise: String? = null,
                @SerializedName("created_at")
                val createdAt: String = "",
                @SerializedName("reset_sent_at")
                val resetSentAt: String? = null,
                @SerializedName("customergroup")
                val customergroup: String? = null,
                @SerializedName("division")
                val division: String? = null,
                @SerializedName("updated_at")
                val updatedAt: String = "",
                @SerializedName("credit_override")
                val creditOverride: String? = null,
                @SerializedName("activecustomer")
                val activecustomer: String? = null,
                @SerializedName("ern_number")
                val ernNumber: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("reset_digest")
                val resetDigest: String? = null,
                @SerializedName("first_name")
                val firstName: String = "",
                @SerializedName("email")
                val email: String = "",
                @SerializedName("company_id")
                val companyId: String? = null,
                @SerializedName("last_login")
                val lastLogin: String = "",
                @SerializedName("address1")
                val address: String? = null,
                @SerializedName("vat")
                val vat: String? = null,
                @SerializedName("last_name")
                val lastName: String = "",
                @SerializedName("profile_picture")
                val profilePicture: ProfilePicture,
                @SerializedName("telephone")
                val telephone: String? = null,
                @SerializedName("confirm_token")
                val confirmToken: String? = null,
                @SerializedName("creditlimitdays")
                val creditlimitdays: String? = null,
                @SerializedName("route")
                val route: String? = null,
                @SerializedName("customercode")
                val customercode: String? = null,
                @SerializedName("password_digest")
                val passwordDigest: String = "",
                @SerializedName("activated")
                val activated: Boolean = false,
                @SerializedName("status")
                val status: String? = null)