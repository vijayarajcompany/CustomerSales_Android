package com.pepsidrc.ui.navigation.ui.orders.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.model.orderlist.OrdersItem
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterOrderListCallback
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_orders.view.*

class AdapterOrderList(
    private val adapterViewClickListener: AdapterViewClickListener<OrdersItem>?,
    val activity: Activity
) : ListAdapter<OrdersItem, AdapterOrderList.ViewHolder>(
    AdapterOrderListCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterOrderList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_orders, parent, false)

        /*  val displayMetrics = DisplayMetrics()
          activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
          val width = displayMetrics.widthPixels

          itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
  */

        return ViewHolder(itemView, activity)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: OrdersItem, adapterViewClick: AdapterViewClickListener<OrdersItem>?) {
            var adapter = AdapterOrderDetailsList(activity)
            var manager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL, false
            )

            itemView.rv_order_item?.layoutManager = manager
            itemView.rv_order_item?.adapter = adapter

            adapter?.submitList(allProducts?.orderItems)
            adapter?.notifyDataSetChanged()
            if (allProducts.isVisible) {
                itemView.tvMoreDetails.setText(AndroidUtils.getString(R.string.less_details))
                itemView.rv_order_item.visibility = View.VISIBLE
            } else if (!allProducts.isVisible) {
                itemView.rv_order_item.visibility = View.GONE
                itemView.tvMoreDetails.setText(AndroidUtils.getString(R.string.more_details))

            }
            itemView.text_order_id_value?.text = allProducts.orderNumber
            itemView.text_order_status_value?.text = allProducts.status
            itemView.text_order_date_value?.text =allProducts.orderDate
            //AndroidUtils.getDateOrder(allProducts?.orderDate)

           // allProducts.orderDate
            itemView.text_order_payment_value?.text = allProducts.order_payment_type

            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_ORDER, adapterPosition
                )
            }
            itemView.rlTrackOrders.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_ORDER, adapterPosition
                )
            }
            itemView.rlMoreDetails.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_MORE_DETAILS, adapterPosition
                )


            }
        }
    }

}