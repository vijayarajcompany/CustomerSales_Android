package com.pepsidrc.ui.navigation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.pepsidrc.BuildConfig
import com.pepsidrc.R
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.model.home.banner.BannerItem

class IndicatorPagerAdapter(private val list: List<BannerItem>) :PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = LayoutInflater.from(container.context).inflate(
            R.layout.item_home_header, container,
            false)
        val ivBanner : SimpleDraweeView = item.findViewById(R.id.ivBanner)

        ImageRequestManager.with(ivBanner)
            .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
            .url(list[position]?.avatarUrl)
            .build()
        container.addView(item)
        return item
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}