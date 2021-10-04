package com.pepsidrc.ui.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.R
import com.pepsidrc.model.PaymentMode
import com.pepsidrc.model.trade_offer.TradeOfferResponsePayload
import com.pepsidrc.model.trade_offer.TradeOffersItem
import kotlinx.android.synthetic.main.item_offer.view.*
import kotlinx.android.synthetic.main.item_offer.view.radio
import kotlinx.android.synthetic.main.item_payment_mode.view.*

class AdapterPaymentMode (
    private val mDataset: List<PaymentMode>,
    internal var recyclerViewItemClickListener: RecyclerViewItemClickListener

) : RecyclerView.Adapter<AdapterPaymentMode.OfferViewHolder>() {
    var radioButton: RadioButton?=null

    init {
        var radio :RadioButton?=null
    }
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): OfferViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_payment_mode, parent, false)

        return OfferViewHolder(v)

    }

    override fun onBindViewHolder(fruitViewHolder: OfferViewHolder, i: Int) {
        fruitViewHolder.tvMode.text = mDataset[i]!!.mode


    }

    override fun getItemCount(): Int {
        return mDataset.size
    }


    inner class OfferViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var tvMode: TextView
        init {
            tvMode = v.tvMode
          //  radioButton = v.radio

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
         //   v.radioP.isChecked=true
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
        fun clickOnItem(data: PaymentMode)
    }
}