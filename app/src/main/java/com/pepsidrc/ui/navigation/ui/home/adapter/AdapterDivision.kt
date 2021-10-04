package com.pepsidrc.ui.navigation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.R
import com.pepsidrc.model.division.DivisionItem
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import com.pepsidrc.model.trade_offer.TradeOffersItem
import kotlinx.android.synthetic.main.item_division.view.*
import kotlinx.android.synthetic.main.item_offer.view.*
import kotlinx.android.synthetic.main.item_payment_mode.view.*
import kotlinx.android.synthetic.main.item_payment_mode.view.radioP

class AdapterDivision (
    private val mDataset: List<DivisionItem>,
    internal var recyclerViewItemClickListener: RecyclerViewItemClickListener

) : RecyclerView.Adapter<AdapterDivision.OfferViewHolder>() {
    var radioButton: RadioButton?=null

    init {
        var radio :RadioButton?=null
    }
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): OfferViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_division, parent, false)

        return OfferViewHolder(v)

    }

    override fun onBindViewHolder(fruitViewHolder: OfferViewHolder, i: Int) {
        fruitViewHolder.tvOffer.text = mDataset[i]!!.description


    }

    override fun getItemCount(): Int {
        return mDataset.size
    }


    inner class OfferViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var tvOffer: TextView
        init {
            tvOffer = v.tvDivision

            v.setOnClickListener(this)
            v.radioP.setOnClickListener {
                if(radioButton==null) {
                    v.radioP.isChecked = true
                }else{
                    radioButton?.isChecked = false
                    v.radioP.isChecked = true
                }
                radioButton = v.radioP

                recyclerViewItemClickListener.clickOnItem(mDataset[this.adapterPosition])
            }
        }

        override fun onClick(v: View) {
            if(radioButton==null) {
                v.radioP.isChecked = true
            }else{
                radioButton?.isChecked = false
                v.radioP.isChecked = true
            }
            radioButton = v.radioP

            recyclerViewItemClickListener.clickOnItem(mDataset[this.adapterPosition])

        }
    }

    interface RecyclerViewItemClickListener {
        fun clickOnItem(data: DivisionItem)
    }
}