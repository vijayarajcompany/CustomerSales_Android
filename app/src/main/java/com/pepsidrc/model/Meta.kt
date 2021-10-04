package com.pepsidrc.model

import com.google.gson.annotations.SerializedName
import com.pepsidrc.model.product.Pagination

data class Meta(@SerializedName("pagination")
                val pagination: Pagination
)