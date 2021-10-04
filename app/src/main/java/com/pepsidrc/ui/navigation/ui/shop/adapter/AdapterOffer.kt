package com.pepsidrc.ui.navigation.ui.shop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.R
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import com.pepsidrc.model.trade_offer.TradeOffersItem
import kotlinx.android.synthetic.main.item_offer.view.*

class AdapterOffer (
    private val mDataset: List<TradeOffersItem>,
    internal var recyclerViewItemClickListener: RecyclerViewItemClickListener

) : RecyclerView.Adapter<AdapterOffer.OfferViewHolder>() {
    var radioButton: RadioButton?=null

    init {
        var radio :RadioButton?=null
    }
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): OfferViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)

        return OfferViewHolder(v)

    }

    override fun onBindViewHolder(fruitViewHolder: OfferViewHolder, i: Int) {
        fruitViewHolder.tvOffer.text = mDataset[i]!!.title


    }

    override fun getItemCount(): Int {
        return mDataset.size
    }


    inner class OfferViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var tvOffer: TextView
        init {
            tvOffer = v.tvOffer

            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if(radioButton==null) {
                v.radio.isChecked = true
            }else{
                radioButton?.isChecked = false
                v.radio.isChecked = true
            }
            radioButton = v.radio

            recyclerViewItemClickListener.clickOnItem(mDataset[this.adapterPosition])

        }
    }

    interface RecyclerViewItemClickListener {
        fun clickOnItem(data: TradeOffersItem)
    }
}