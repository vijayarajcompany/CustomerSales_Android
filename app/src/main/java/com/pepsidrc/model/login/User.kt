package com.pepsidrc.model.login

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("company_id")
                val companyId: String? = null,
                @SerializedName("last_login")
                val lastLogin: String = "",
                @SerializedName("document")
                val document: Document,
                @SerializedName("last_name")
                val lastName: String? = "",
                @SerializedName("created_at")
                val createdAt: String = "",
                @SerializedName("profile_picture")
                val profilePicture: ProfilePicture,
                @SerializedName("reset_sent_at")
                val resetSentAt: String? = null,
                @SerializedName("updated_at")
                val updatedAt: String = "",
                @SerializedName("ern_number")
                val ernNumber: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("first_name")
                val firstName: String = "",
                @SerializedName("email")
                val email: String = "",
                @SerializedName("cart_item_count")
                val cart_item_count: String = "",
                @SerializedName("activated")
                val activated: Boolean = false,
                @SerializedName("status")
                val status: String? = null)