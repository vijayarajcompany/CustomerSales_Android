package com.pepsidrc.model.division

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.Meta

data class DivisionListingResponse(@SerializedName("data")
                                   val data: DivisionData,
                                   @SerializedName("success")
                                   val success: Boolean = false,
                                   @SerializedName("meta")
                                   val meta: Meta
)