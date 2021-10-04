package com.pepsidrc.model.cart.cancelOrder

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("tradeoffer_id")
                val tradeofferId: String = "",
                @SerializedName("paymentterms")
                val paymentterms: String = "",
                @SerializedName("city")
                val city: String = "",
                @SerializedName("creditlimitamount")
                val creditlimitamount: String = "",
                @SerializedName("document")
                val document: Document,
                @SerializedName("excise")
                val excise: String = "",
                @SerializedName("created_at")
                val createdAt: String = "",
                @SerializedName("reset_sent_at")
                val resetSentAt: String?,
                @SerializedName("customergroup")
                val customergroup: String = "",
                @SerializedName("division")
                val division: String = "",
                @SerializedName("updated_at")
                val updatedAt: String = "",
                @SerializedName("credit_override")
                val creditOverride: String = "",
                @SerializedName("activecustomer")
                val activecustomer: String = "",
                @SerializedName("ern_number")
                val ernNumber: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("reset_digest")
                val resetDigest: String?,
                @SerializedName("first_name")
                val firstName: String = "",
                @SerializedName("email")
                val email: String = "",
                @SerializedName("company_id")
                val companyId: String?,
                @SerializedName("last_login")
                val lastLogin: String = "",
                @SerializedName("address1")
                val address: String = "",
                @SerializedName("vat")
                val vat: String = "",
                @SerializedName("last_name")
                val lastName: String = "",
                @SerializedName("profile_picture")
                val profilePicture: ProfilePicture,
                @SerializedName("telephone")
                val telephone: String?,
                @SerializedName("confirm")
                val confirm: Boolean = false,
                @SerializedName("confirm_token")
                val confirmToken: String?,
                @SerializedName("creditlimitdays")
                val creditlimitdays: String = "",
                @SerializedName("route")
                val route: String = "",
                @SerializedName("customercode")
                val customercode: String = "",
                @SerializedName("password_digest")
                val passwordDigest: String = "",
                @SerializedName("activated")
                val activated: Boolean = false,
                @SerializedName("status")
                val status: String?)