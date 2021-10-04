package com.pepsidrc.model.subcategories

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta
import com.pepsidrc.model.signUp.ErrorsItem


data class SubcategoriesResponsePayload(@SerializedName("data")
                                        val data: SubCategoriesData,
                                        @SerializedName("success")
                                        val success: Boolean = false,
                                        @SerializedName("meta")
                                        val meta: Meta,
                                        @SerializedName("message")
                                        val message: String = "",
                                        @SerializedName("errors")
                                        val errors: List<ErrorsItem>?)