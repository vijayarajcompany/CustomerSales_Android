package com.pepsidrc.ui.splash

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.pepsidrc.base.AbstractViewModel
import com.pepsidrc.base.SingleLiveEvent
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.model.SearchEvent
import com.pepsidrc.model.categories.CategoriesResponsePayload
import com.pepsidrc.model.subcategories.SubcategoriesResponsePayload
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import com.pepsidrc.ui.login.LoginRepository
import com.pepsidrc.ui.navigation.ui.shop.ShopRepository
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.Logger
import retrofit2.HttpException
const val SPLASH_NEXT_HOME_ACTIVITY = 1
const val SPLASH_NEXT_ON_LOGIN_ACTIVITY = 2
class SplashViewModel (private val preferenceManager: PreferenceManager
) : AbstractViewModel() {

    val nextIntent = MutableLiveData<Int>()

    fun loadData() {
        Handler().postDelayed({

            if (!preferenceManager.isUserLoggedIn()) {
                nextIntent.postValue(SPLASH_NEXT_ON_LOGIN_ACTIVITY)
            }  else {
                nextIntent.postValue(SPLASH_NEXT_HOME_ACTIVITY)
            }
        }, 1500)
    }

}