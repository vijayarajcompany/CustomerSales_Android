package com.pepsidrc.ui.navigation.ui.home

import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.division.DivisionListingResponse
import com.pepsidrc.model.division.DivisionUpdateRequestPayload
import com.pepsidrc.model.home.banner.BannerResponsePayload
import com.pepsidrc.model.profile.UserDetailResponse
import io.reactivex.Single

interface HomeRepository {
    fun getCategories() : Single<CategoriesResponsePayload>
    fun getBanners() : Single<BannerResponsePayload>
    fun getDivision() : Single<DivisionListingResponse>
    fun updateDivision(data: DivisionUpdateRequestPayload) : Single<UserDetailResponse>

}