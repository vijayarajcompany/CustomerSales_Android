package com.pepsidrc.ui.navigation.ui.home


import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.division.DivisionListingResponse
import com.pepsidrc.model.division.DivisionUpdateRequestPayload
import com.pepsidrc.model.home.banner.BannerResponsePayload
import com.pepsidrc.model.profile.UserDetailResponse
import com.pepsidrc.network.AppRestApiFast
import io.reactivex.Single

class HomeRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : HomeRepository {
    override fun updateDivision(data: DivisionUpdateRequestPayload): Single<UserDetailResponse> {
        return restApi.updateDivisions(pre.getBearerToken(),data)
    }

    override fun getDivision(): Single<DivisionListingResponse> {
        return restApi.getDivisions(pre.getBearerToken())
    }

    override fun getBanners(): Single<BannerResponsePayload> {
        return restApi.getBanners()
    }

    override fun getCategories(): Single<CategoriesResponsePayload> {
        return restApi.getCategories(pre.getBearerToken())
    }

}