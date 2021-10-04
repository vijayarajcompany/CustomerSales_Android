package com.pepsidrc.ui.filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.R
import kotlinx.android.synthetic.main.item_filter_type.view.*

class AdapterFilterType (
    private val mDataset: Array<String>,
    internal var recyclerViewItemClickListener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<AdapterFilterType.FilterTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): FilterTypeViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_filter_type, parent, false)

        return FilterTypeViewHolder(v)

    }

    override fun onBindViewHolder(fruitViewHolder: FilterTypeViewHolder, i: Int) {
        fruitViewHolder.tvFilterType.text = mDataset[i]


    }

    override fun getItemCount(): Int {
        return mDataset.size
    }


    inner class FilterTypeViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var tvFilterType: TextView

        init {
            tvFilterType = v.tv_filter_type_name
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            recyclerViewItemClickListener.clickOnItem(mDataset[this.adapterPosition])

        }
    }

    interface RecyclerViewItemClickListener {
        fun clickOnItem(data: String)
    }
}