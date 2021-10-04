package com.pepsidrc.di


import android.app.Application
import com.pepsidrc.interfaces.ApplicationSchedulerProvider
import com.pepsidrc.interfaces.SchedulerProvider
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.ui.cart.CartRepository
import com.pepsidrc.ui.cart.CartRepositoryImpl
import com.pepsidrc.ui.cart.CartViewModel
import com.pepsidrc.ui.coupons.CouponRepository
import com.pepsidrc.ui.coupons.CouponRepositoryImpl
import com.pepsidrc.ui.coupons.CouponViewModel
import com.pepsidrc.ui.login.LoginRepository
import com.pepsidrc.ui.login.LoginRepositoryImpl
import com.pepsidrc.ui.login.LoginViewModel
import com.pepsidrc.ui.navigation.ui.home.HomeRepository
import com.pepsidrc.ui.navigation.ui.home.HomeRepositoryImpl
import com.pepsidrc.ui.navigation.ui.home.HomeViewModel
import com.pepsidrc.ui.navigation.ui.orders.OrderListRepository
import com.pepsidrc.ui.navigation.ui.orders.OrderListRepositoryImpl
import com.pepsidrc.ui.navigation.ui.orders.OrdersViewModel
import com.pepsidrc.ui.navigation.ui.shop.ShopRepository
import com.pepsidrc.ui.navigation.ui.shop.ShopRepositoryImpl
import com.pepsidrc.ui.navigation.ui.shop.ShopViewModel
import com.pepsidrc.ui.orderDetails.OrderDetailRepository
import com.pepsidrc.ui.orderDetails.OrderDetailRepositoryImpl
import com.pepsidrc.ui.orderDetails.OrderDetailsViewModel
import com.pepsidrc.ui.productDetail.ProductsDetailViewModel
import com.pepsidrc.ui.products.ProductRepository
import com.pepsidrc.ui.products.ProductRepositoryImpl
import com.pepsidrc.ui.products.ProductsViewModel
import com.pepsidrc.ui.profile.ProfileRepository
import com.pepsidrc.ui.profile.ProfileRepositoryImpl
import com.pepsidrc.ui.profile.ProfileViewModel
import com.pepsidrc.ui.resetPassword.ResetPasswordRepository
import com.pepsidrc.ui.resetPassword.ResetPasswordRepositoryImpl
import com.pepsidrc.ui.resetPassword.ResetPasswordViewModel
import com.pepsidrc.ui.search.SearchRepository
import com.pepsidrc.ui.search.SearchRepositoryImpl
import com.pepsidrc.ui.search.SearchViewModel
import com.pepsidrc.ui.signup.SignUpRepository
import com.pepsidrc.ui.signup.SignUpRepositoryImpl
import com.pepsidrc.ui.signup.SignUpViewModel
import com.pepsidrc.ui.splash.SplashViewModel
import com.pepsidrc.ui.subcategories.SubCategoriesViewModel
import com.pepsidrc.ui.subcategories.SubcategoriesRepository
import com.pepsidrc.ui.subcategories.SubcategoriesRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object AppModule {


    /*  bean { PreferenceManager(androidApplication()) }

    bean { ActivityLifecycleManager() }

//     for splash activity
    viewModel { AuthViewModel(get()) }
    bean { AuthnticationRepository() }*/
    val appModule: Module = module {
        single { getSharedPrefrence(androidApplication()) }
        single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
        viewModel { LoginViewModel(get(),get()) }
        single { ApplicationSchedulerProvider() as SchedulerProvider }

        single<SignUpRepository> { SignUpRepositoryImpl(get(), get()) }
        viewModel { SignUpViewModel(get(),get()) }

        single<ResetPasswordRepository> { ResetPasswordRepositoryImpl(get(), get()) }
        viewModel { ResetPasswordViewModel(get(),get()) }

        single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
        viewModel { ProfileViewModel(get(),get()) }

        single<HomeRepository> { HomeRepositoryImpl(get(), get()) }
        viewModel { HomeViewModel(get(),get()) }

        single<ShopRepository> { ShopRepositoryImpl(get(), get()) }
        viewModel { ShopViewModel(get(),get()) }

        single<SubcategoriesRepository> { SubcategoriesRepositoryImpl(get(), get()) }
        viewModel { SubCategoriesViewModel(get(),get()) }

        viewModel { SplashViewModel(get()) }


        single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
        viewModel { ProductsViewModel(get(),get()) }


        single<OrderListRepository> { OrderListRepositoryImpl(get(), get()) }
        viewModel { OrdersViewModel(get(),get()) }

        single<CartRepository> { CartRepositoryImpl(get(), get()) }
        viewModel { CartViewModel(get(),get(),get()) }

        single<CouponRepository> { CouponRepositoryImpl(get(), get()) }
        viewModel { CouponViewModel(get(),get()) }

        single<OrderDetailRepository> { OrderDetailRepositoryImpl(get(), get()) }
        viewModel { OrderDetailsViewModel(get(),get()) }

        single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
        viewModel { SearchViewModel(get(),get()) }

        viewModel { ProductsDetailViewModel(get(),get()) }
        /* single(named("")) { PreferenceManager(get(named(OKHTTP))) }


      viewModel { MainViewModel(get(),get(),get(),get(),get()) }
        single<MainRepository> { MainRepositoryImpl(get(named(NetworkModule.RETROFIT_AIRLINES_API))) }

    }*/


    }


    fun getSharedPrefrence(app: Application): PreferenceManager {
        return PreferenceManager(app)
    }
}