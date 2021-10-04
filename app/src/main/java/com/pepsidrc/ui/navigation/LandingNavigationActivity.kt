package com.pepsidrc.ui.navigation

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pepsidrc.util.UiUtils

import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import com.pepsidrc.R
import com.pepsidrc.base.BaseFragment
import com.pepsidrc.interfaces.SortImpl
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.ui.cart.CartDeatilActivity
import com.pepsidrc.ui.navigation.ui.home.HomeFragment
import com.pepsidrc.ui.navigation.ui.more.MoreFragment
import com.pepsidrc.ui.navigation.ui.orders.OrdersFragment
import com.pepsidrc.ui.navigation.ui.shop.ShopFragment
import com.pepsidrc.ui.navigation.ui.shop.SortByBottomSheet
import com.pepsidrc.ui.search.SearchActivity
import com.pepsidrc.ui.signup.ActivitySignUp
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config

import kotlinx.android.synthetic.main.activity_landing_navigation.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

const val INDEX_HOME = FragNavController.TAB1
const val INDEX_SHOP = FragNavController.TAB2
const val INDEX_ORDERS = FragNavController.TAB3
const val INDEX_MORE = FragNavController.TAB4


class LandingNavigationActivity : AppCompatActivity(), BaseFragment.FragmentNavigation,
    FragNavController.TransactionListener, FragNavController.RootFragmentListener {


    override val numberOfRootFragments: Int = 4
    var preferenceManager: PreferenceManager? = null

    private val fragNavController: FragNavController =
        FragNavController(supportFragmentManager, R.id.container)

    override fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?) {
        val options = FragNavTransactionOptions.newBuilder()
        options.reordering = true
        sharedElementList?.let {
            it.forEach { pair ->
                options.addSharedElement(pair)
            }
        }
        fragNavController.pushFragment(fragment, options.build())
    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

    }


    override fun onFragmentTransaction(
        fragment: Fragment?,
        transactionType: FragNavController.TransactionType
    ) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

    }

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_HOME -> return HomeFragment.getInstance(0)
            INDEX_SHOP -> return ShopFragment.getInstance(0)
            INDEX_ORDERS -> return OrdersFragment.getInstance(0)
            INDEX_MORE -> return MoreFragment.getInstance(0)
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return true
    }


    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context, f: Int): Intent? {
            val intent = Intent(context, LandingNavigationActivity::class.java)
            intent.putExtra(KEY_TAB, f)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_navigation)
        preferenceManager = PreferenceManager(this)

        fragNavController.apply {
            transactionListener = this@LandingNavigationActivity
            rootFragmentListener = this@LandingNavigationActivity
            createEager = false
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {
                    Log.e("DEBUG", message, throwable)
                }
            }

            defaultTransactionOptions = FragNavTransactionOptions.newBuilder().customAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            ).build()
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH

            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    bottomBar.selectTabAtPosition(index)
                }
            })
        }


        var i = intent.getIntExtra(KEY_TAB, 1)
        fragNavController.initialize(INDEX_HOME, savedInstanceState)
        val initial = savedInstanceState == null
        if (initial) {
            bottomBar.selectTabAtPosition(INDEX_HOME)
        }

        if (i === 3) {
            bottomBar.selectTabAtPosition(INDEX_ORDERS)

        }
        bottomBar.setOnTabSelectListener({ tabId ->
            when (tabId) {
                R.id.navigation_home -> {
                    fragNavController.switchTab(INDEX_HOME)
                    if (fragNavController.isRootFragment && fragNavController.currentFrag is HomeFragment) {
                      //  (fragNavController.currentFrag as HomeFragment).showDivisionDialog()
                        setTitleOnBar(AndroidUtils.getString(R.string.dubai_refreshments))
                        setBack(false)
                    }

                }
                R.id.navigation_shop -> {
                    fragNavController.switchTab(INDEX_SHOP)

                }
                R.id.navigation_orders -> {
                    fragNavController.switchTab(INDEX_ORDERS)
                    setTitleOnBar(AndroidUtils.getString(R.string.order_list))
                    setBack(false)
                }
                R.id.navigation_more -> {
                    fragNavController.switchTab(INDEX_MORE)
                    setTitleOnBar(AndroidUtils.getString(R.string.more))
                    setBack(false)
                }
            }
        }, initial)

        bottomBar.setOnTabReselectListener { fragNavController.clearStack() }

        fl_left_img_container.setOnClickListener {

            onBackPressed()
        }
        iv_search.setOnClickListener {

            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                SearchActivity.getIntent(this),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )

        }
        iv_cart.setOnClickListener {
            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                CartDeatilActivity.getIntent(this)
            )
        }
    }

    override fun onBackPressed() {
        if (fragNavController.popFragment().not()) {
            super.onBackPressed()
        }
    }

    fun setTitleOnBar(title: String?) {
        tv_tool_title.text = title
    }
    fun switchTabShop() {
        bottomBar.selectTabAtPosition(INDEX_SHOP)
    }
    fun setBack(isShow: Boolean) {
        if (isShow) {
            fl_left_img_container.visibility = View.VISIBLE
        } else {
            fl_left_img_container.visibility = View.INVISIBLE

        }
    }

    fun setCounter(isShow: Boolean, text: String?) {
        if (text.equals("") || text.equals("0")) {
            ivCounter.visibility = View.INVISIBLE
        } else {
            ivCounter.setText(text)
            ivCounter.visibility = View.VISIBLE

        }
    }

    override fun onResume() {
        super.onResume()
        if (preferenceManager?.getBooleanPreference(Config.SharedPreferences.PROPERTY_IS_CART)!!) {
            ivCounter.visibility = View.VISIBLE
            ivCounter.setText(
                preferenceManager?.getStringPreference(
                    Config.SharedPreferences.PROPERTY_IS_CART_VALUE,
                    ""
                )
            )


        } else {
            ivCounter.visibility = View.INVISIBLE

        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        fragNavController.onSaveInstanceState(outState!!)

    }

    fun sortBy(sortImpl: SortImpl, sort_by : String) {
        val sortByBottomSheet = SortByBottomSheet(this, sortImpl,sort_by)

        if (sortByBottomSheet.isAdded) {
            sortByBottomSheet.dismiss()
        }
        sortByBottomSheet.isCancelable = false
        sortByBottomSheet.show(supportFragmentManager, SortByBottomSheet.TAG)
    }


    public fun getVisibleFragment(): Boolean {
        if (fragNavController.isRootFragment && fragNavController.currentFrag is HomeFragment) {
            return true
        }
        return false
    }

    public fun getVisibleFragmentOrders(): Boolean {
        if (fragNavController.isRootFragment && fragNavController.currentFrag is OrdersFragment) {
            return true
        }
        return false
    }

    public fun getVisibleFragmentShop(): Boolean {
        if (fragNavController.isRootFragment && fragNavController.currentFrag is ShopFragment) {
            return true
        }
        return false
    }

    public fun getVisibleFragmentMore(): Boolean {
        if (fragNavController.isRootFragment && fragNavController.currentFrag is MoreFragment) {
            return true
        }
        return false
    }

}
