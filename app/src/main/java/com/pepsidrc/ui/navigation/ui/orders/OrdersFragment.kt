package com.pepsidrc.ui.navigation.ui.orders

import android.app.Activity
import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepsidrc.R
import com.pepsidrc.base.BaseFragment
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.model.orderlist.OrderListResponsePayload
import com.pepsidrc.model.orderlist.OrdersItem
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.ui.navigation.ui.orders.adapter.AdapterOrderList
import com.pepsidrc.ui.orderDetails.OrderDetailActivity
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : BaseFragment<OrdersViewModel>(OrdersViewModel::class),
    AdapterViewClickListener<OrdersItem> {
    override fun onClickAdapterView(objectAtPosition: OrdersItem, viewType: Int, position: Int) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_ORDER -> {

                let {

                    activity.let {
                        gotoOrderDetails(it, objectAtPosition)
                    }
                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_MORE_DETAILS -> {

                let {

                    activity.let {
                        if (orderList?.get(position)!!.isVisible) {
                            orderList?.get(position)!!.isVisible = false
                        } else {
                            orderList?.get(position)!!.isVisible = true
                        }
                        adapterOrderList?.submitList(orderList)
                        adapterOrderList?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_orders


    private var adapterOrderList: AdapterOrderList? = null
    internal var orderList: List<OrdersItem>? = null

    companion object {

        fun getInstance(instance: Int): OrdersFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = OrdersFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderList = ArrayList()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var manager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rv_orders.layoutManager = manager

        activity?.let {
            adapterOrderList = AdapterOrderList(this, it)

        }
        rv_orders.adapter = adapterOrderList
        subscribeLoading()
        subscribeUi()
        getData()

    }

    private fun gotoOrderDetails(context: Activity?, objectAtPosition: OrdersItem) {
        startActivity(
            OrderDetailActivity.getIntent(context, objectAtPosition),
            ActivityOptions.makeSceneTransitionAnimation(context).toBundle()
        )
    }

    private fun getData() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getOrderList()
        }
    }

    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentOrders()) {

            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.order_list))
            (activity as LandingNavigationActivity).setBack(true)
        }
    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.let {
                UiUtils.showInternetDialog(activity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.orderListModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)
        })

    }

    private fun showData(data: OrderListResponsePayload?) {
        (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.order_list))
        (activity as LandingNavigationActivity).setBack(true)
        orderList = data?.data?.orders
        adapterOrderList?.submitList(orderList)
        adapterOrderList?.notifyDataSetChanged()

    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
}