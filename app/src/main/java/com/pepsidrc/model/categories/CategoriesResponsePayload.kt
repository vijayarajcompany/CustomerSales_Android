package com.pepsidrc.model.categories

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta

data class CategoriesResponsePayload(@SerializedName("data")
                                     val data: CategoryResponse,
                                     @SerializedName("success")
                                     val success: Boolean = false,
                                     @SerializedName("meta")
                                     val meta: Meta
                                     )