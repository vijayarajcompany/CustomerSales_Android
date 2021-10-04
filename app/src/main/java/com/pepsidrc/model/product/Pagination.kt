package com.pepsidrc.model.product

import com.google.gson.annotations.SerializedName

data class Pagination(@SerializedName("next_page")
                      var nextPage: Int = 0,
                      @SerializedName("total_count")
                      var totalCount: Int = 0,
                      @SerializedName("total_pages")
                      var totalPages: Int = 0,
                      @SerializedName("prev_page")
                      var prevPage: String? ,
                      @SerializedName("current_page")
                      var currentPage: Int = 0)