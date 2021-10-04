package com.pepsidrc.model.orderlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("tradeoffer_id")
    var tradeofferId: String?,
    @SerializedName("paymentterms")
    var paymentterms: String?,
    @SerializedName("city")
    var city: String?,
    @SerializedName("creditlimitamount")
    var creditlimitamount: String?,
    @SerializedName("document")
    var document: Document,
    @SerializedName("excise")
    var excise: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("reset_sent_at")
    var resetSentAt: String?,
    @SerializedName("customergroup")
    var customergroup: String?,
    @SerializedName("division")
    var division: String?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("credit_override")
    var creditOverride: String?,
    @SerializedName("activecustomer")
    var activecustomer: String?,
    @SerializedName("ern_number")
    var ernNumber: String?,
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("reset_digest")
    var resetDigest: String?,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("company_id")
    var companyId: String?,
    @SerializedName("last_login")
    var lastLogin: String?,
    @SerializedName("address1")
    var address: String?,
    @SerializedName("vat")
    var vat: String?,
    @SerializedName("last_name")
    var lastName: String?,
    @SerializedName("profile_picture")
    var profilePicture: ProfilePicture,
    @SerializedName("telephone")
    var telephone: String?,
    @SerializedName("confirm_token")
    var confirmToken: String?,
    @SerializedName("creditlimitdays")
    var creditlimitdays: String?,
    @SerializedName("route")
    var route: String?,
    @SerializedName("customercode")
    var customercode: String?,
    @SerializedName("password_digest")
    var passwordDigest: String?,
    @SerializedName("activated")
    var activated: Boolean = false,
    @SerializedName("status")
    var status: String?
): Parcelable