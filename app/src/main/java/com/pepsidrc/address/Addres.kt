package com.pepsidrc.address

import com.google.gson.annotations.SerializedName

data class Addres(@SerializedName("address")
                  val address: String?,
                  @SerializedName("latitude")
                  val latitude: String?,
                  @SerializedName("id")
                  val id: Int = 0,
                  @SerializedName("title")
                  val title: String?,
                  @SerializedName("mobile_number")
                  val mobileNumber: String?)