package com.pepsidrc.model.subcategories

import com.google.gson.annotations.SerializedName

data class SubCategoriesData(@SerializedName("subcategories")
                             val subcategories: List<SubcategoriesItem>?)