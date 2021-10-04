package com.pepsidrc.model.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by AB on 6/15/2018.
 */

class ProductsResponse : Serializable {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: List<Product>? = null
    @SerializedName("meta")
    @Expose
    var meta: List<Any>? = null
    @SerializedName("errors")
    @Expose
    var errors: List<Any>? = null
}
