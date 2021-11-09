package com.pepsidrc.ui.subcategories.adapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ScalingUtils

import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.model.subcategories.SubcategoriesItem
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterSubCategoryCallback
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_sub_category.view.*
import kotlinx.android.synthetic.main.item_sub_category.view.tv_product_name

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import java.util.*


class AdapterSubCategories(
    private val adapterViewClickListener: AdapterViewClickListener<SubcategoriesItem>?,
    val activity: Activity
) : ListAdapter<SubcategoriesItem, AdapterSubCategories.ViewHolder>(
    AdapterSubCategoryCallback()
)
{

      val context: Context? = null
//    val Fragment.packageName get() = activity?.packageName

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterSubCategories.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_sub_category, parent, false)

      /*  val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
*/


      val ss =  parent.getContext().getPackageName();

        print(ss);

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position,adapterViewClickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(product: SubcategoriesItem,position: Int, adapterViewClick: AdapterViewClickListener<SubcategoriesItem>?) {
            var adapter = AdapterSubcategoriesPacks( activity)
            var manager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL, false
            )

            itemView.rv_packs_sub_cat?.layoutManager = manager
            itemView.rv_packs_sub_cat?.adapter = adapter

           // adapter?.submitList(product?.packs)
            //adapter?.notifyDataSetChanged()
            itemView.tv_product_name?.text = product.name
            if(product?.image==null){

//                ImageRequestManager.with(itemView.ivProduct_sub)
//                    .setPlaceholderImage(R.drawable.no_image_icon)
//                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
//                    .build()

//                ImageRequestManager.with(itemView.ivProduct_sub)
//                    .setPlaceholderImage(Utils.GetDrawableByName("no_image_icon",this ))
//                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
//                    .build()

//              val id = resources.getIdentifier("your_resource_name", "drawable", this?.getPackageName())
//              val id = context.resources.getIdentifier("my_image", "drawable", context.packageName)

                val res: Class<*> = R.drawable::class.java
                val field = res.getField("no_image_icon")
                val drawableId: Int = field.getInt(null)

                ImageRequestManager.with(itemView.ivProduct_sub)
                    .setPlaceholderImage(drawableId)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()
            }
            else{

//              var imageName =  product?.image?.avatar_url?.substringBefore(".jpg").substringAfter("avatar/").substringAfter("/")
                var imageName =  product.name
                imageName = imageName.replace( "%20" , "_");
                imageName = imageName.replace( " " , "_");

                try {
                    val res: Class<*> = R.drawable::class.java
                    val field = res.getField(imageName.toLowerCase())
                    val drawableId: Int = field.getInt(null)

                    ImageRequestManager.with(itemView.ivProduct_sub)
                        .setPlaceholderImage(drawableId)
                        .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .build()
                }
                catch (e:Exception) {
                    ImageRequestManager.with(itemView.ivProduct_sub)
                        .setPlaceholderImage(R.drawable.no_image_icon)
                        .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .build()
                }
                finally {

                }




//                    ImageRequestManager.with(itemView.ivProduct_sub)
//                    .url(product?.image?.avatar_url)
//                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
//                    .build()

            }
            itemView.btnBuy.setOnClickListener {

                val SubCategoryPg_Buy_Btn_event = AdjustEvent("h13eyt")
                SubCategoryPg_Buy_Btn_event.setCallbackId("SubCategoryPg_Buy_Btn_event");
                SubCategoryPg_Buy_Btn_event.addCallbackParameter("SubCategoryPg_Buy_Btn_Tapped", product.name);
                Adjust.trackEvent(SubCategoryPg_Buy_Btn_event)

                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_SUB_CATEGORY, adapterPosition
                )

            }
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_SUB_CATEGORY, adapterPosition
                )
            }
        }
    }


}