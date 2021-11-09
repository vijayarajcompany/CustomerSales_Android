package com.pepsidrc.ui.navigation.ui.home.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ScalingUtils
import com.pepsidrc.BuildConfig

import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_category_home.view.*
import kotlinx.android.synthetic.main.item_sub_category.view.*

class AdapterHomeCategories(
    private val adapterViewClickListener: AdapterViewClickListener<CategoriesItem>?,
    val activity: Activity
) : ListAdapter<CategoriesItem, AdapterHomeCategories.ViewHolder>(
    AdapterHomeCategoryCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterHomeCategories.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_category_home, parent, false)

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

        fun bind(allProducts: CategoriesItem, adapterViewClick: AdapterViewClickListener<CategoriesItem>?) {

            itemView.txtName?.text = allProducts.name

//            ImageRequestManager.with(itemView.imgSource)
//                .url(allProducts.image?.avatar?.url)
//                .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
//                .build()

//            ImageRequestManager.with(itemView.imgSource)
//                .setPlaceholderImage(R.drawable.cart)
//                .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
//                .build()

            var imageName:String? =  allProducts.name
            imageName = imageName?.replace( "%20" , "_");
            imageName = imageName?.replace( " " , "_");

            try {
                val res: Class<*> = R.drawable::class.java
                val field = res.getField(imageName?.toLowerCase())
//                val field = res.getField(imageName)
                val drawableId: Int = field.getInt(null)

                ImageRequestManager.with(itemView.imgSource)
                    .setPlaceholderImage(drawableId)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()
            }
            catch (e:Exception) {
                ImageRequestManager.with(itemView.imgSource)
                    .setPlaceholderImage(R.drawable.no_image_icon)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()
            }


            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY, adapterPosition
                )
            }

        }
    }

}