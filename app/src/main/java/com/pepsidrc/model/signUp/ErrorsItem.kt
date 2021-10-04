package com.pepsidrc.model.signUp

import com.google.gson.annotations.SerializedName

data class ErrorsItem(
    @SerializedName("field")
    val field: String? = "",
    @SerializedName("resource")
    val resource: String? = "",
    @SerializedName("field_label")
    val fieldLabel: String? = "",
    @SerializedName("detail")
    val detail: String? = "",
    @SerializedName("error")
    val error: String? = ""
)