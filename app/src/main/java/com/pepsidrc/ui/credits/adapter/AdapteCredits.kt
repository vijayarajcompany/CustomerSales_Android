package com.pepsidrc.ui.credits.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.pepsidrc.R
import com.pepsidrc.callbacks.AdapterViewClickListener
import com.pepsidrc.model.more.MoreItems
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterCategoryCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterMoreCallback
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.item_category_home.view.*
import kotlinx.android.synthetic.main.item_more.view.*

class AdapteCredits(
    private val adapterViewClickListener: AdapterViewClickListener<MoreItems>?,
    val activity: Activity
) : ListAdapter<MoreItems, AdapteCredits.ViewHolder>(
    AdapterMoreCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapteCredits.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_credits, parent, false)
        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position,adapterViewClickListener)
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(More: MoreItems,position: Int, adapterViewClick: AdapterViewClickListener<MoreItems>?) {

          //  itemView.iv_more?.setImageResource(More.id!!)
          //  itemView.text_item?.text = More.name
            /*itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    More,
                    Config.AdapterClickViewTypes.CLICK_VIEW_MORE, adapterPosition
                )
            }*/
        }
    }

}