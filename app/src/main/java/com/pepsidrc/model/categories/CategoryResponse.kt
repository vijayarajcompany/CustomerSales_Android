package com.pepsidrc.model.categories

import com.google.gson.annotations.SerializedName

data class CategoryResponse(@SerializedName("categories")
                val categories: ArrayList<CategoriesItem>?)