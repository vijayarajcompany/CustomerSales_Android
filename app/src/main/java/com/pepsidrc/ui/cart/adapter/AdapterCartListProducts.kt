package com.pepsidrc.ui.cart.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ScalingUtils
import com.pepsidrc.util.UiUtils

import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.model.cart.cartItems.OrderItemsItem
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterCartListProductsCallback
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_cart.view.*

class AdapterCartListProducts(
    private val adapterViewClickListener: AdapterViewClickListener<OrderItemsItem>?,
    val activity: Activity
) : ListAdapter<OrderItemsItem, AdapterCartListProducts.ViewHolder>(
    AdapterCartListProductsCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_cart, parent, false)

        /*  val displayMetrics = DisplayMetrics()
          activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
          val width = displayMetrics.widthPixels

          itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
  */

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            position,
            adapterViewClickListener
        )
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            product: OrderItemsItem,
            position: Int,
            adapterViewClick: AdapterViewClickListener<OrderItemsItem>?
        ) {


            itemView.text_order_name_value?.text = product?.itemMaster?.name
            itemView.tvQuantity?.setText(product?.quantity.toString())
            itemView.text_price_value.text =
                AndroidUtils.getString(R.string.price_type) + "\n" + product.total_amount
          //  itemView.text_order_pack_size_value.text = product?.packSize.toString()

            product?.itemMaster?.images?.let {
                if(it.size>0){
                    ImageRequestManager.with(itemView.imgSource)
                        .url(it.get(0)?.avatar_url)
                        .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .build()
                }
                else{
                    ImageRequestManager.with(itemView.imgSource)
                        .setPlaceholderImage(R.drawable.no_image_icon)
                        .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .build()

                }

            }

            /*     itemView.btnAddToCart.setOnClickListener {

                     adapterViewClick?.onClickAdapterView(
                         product,
                         Config.AdapterClickViewTypes.CLICK_ADD_TO_CART_PRODUCT, adapterPosition
                     )
                 }*/

            itemView.tvQuantity.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    UiUtils.hideSoftKeyboard(activity)

                    product.quantity=itemView.tvQuantity.text.toString().toInt()
                    adapterViewClick?.onClickAdapterView(
                        product,
                        Config.AdapterClickViewTypes.CLICK_VIEW_QUANTITY_CHANGED, adapterPosition
                    )
                    true
                } else {
                    false
                }
            }
            itemView.rlPlus.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PLUS_PRODUCT, adapterPosition
                )
            }
            itemView.rlMinus.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_MINUS_PRODUCT, adapterPosition
                )
            }
            itemView.img_delete.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_DELETE_PRODUCT, adapterPosition
                )
            }
           /* itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT, adapterPosition
                )
            }*/
        }
    }

}