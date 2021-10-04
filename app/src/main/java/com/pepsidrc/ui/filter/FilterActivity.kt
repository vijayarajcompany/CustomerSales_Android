package com.pepsidrc.ui.filter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import android.os.Bundle
import android.transition.Visibility
import android.util.Log
import android.view.View
import android.widget.Toast

import com.pepsidrc.R
import com.pepsidrc.model.brand.BrandItem
import com.pepsidrc.model.categories.CategoriesItem
import com.pepsidrc.model.filter.FilterDefaultMultipleListModel
import com.pepsidrc.model.filter.MainFilterModel
import com.pepsidrc.ui.filter.adapter.FilterRecyclerAdapter
import com.pepsidrc.ui.filter.adapter.FilterValRecyclerAdapter
import com.pepsidrc.ui.login.LoginActivity
import com.pepsidrc.utils.AndroidUtils
import com.stfalcon.pricerangebar.model.BarEntry
import kotlinx.android.synthetic.main.activity_filter_activity.*
import kotlinx.android.synthetic.main.app_custom_tool_bar_filter.*
import org.jetbrains.anko.Android

import java.util.Arrays
import kotlin.collections.ArrayList

class FilterActivity : AppCompatActivity() {
    private var adapter: FilterRecyclerAdapter? = null
    private var filterValAdapter: FilterValRecyclerAdapter? = null
    private var catList = ArrayList<CategoriesItem>()
    private var brandList = ArrayList<BrandItem>()

    private var brands = ArrayList<String>()
    private var prices = ArrayList<String>()
    private var categoryMultipleListModels = ArrayList<FilterDefaultMultipleListModel>()
    private var brandMultipleListModels = ArrayList<FilterDefaultMultipleListModel>()
    private val priceMultipleListModels = ArrayList<FilterDefaultMultipleListModel>()
    private val filterModels = ArrayList<MainFilterModel>()
    private var rootFilters: List<String>? = null
    private var categorySelected = ArrayList<String?>()
    private var categorySelectedIds = ArrayList<Int?>()

    private var priceSelected = ArrayList<String?>()
    private var brandSelected = ArrayList<String?>()
    private var brandSelectedIds = ArrayList<Int?>()
    private var rangeBarEntries = java.util.ArrayList<BarEntry>()
    internal var minPrice: String? = "0"
    internal var maxPrice: String? = "200"
    internal var minPriceS: String? = "0"
    internal var maxPriceS: String? = "200"
    //   private var catList: ArrayList<CategoriesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_activity)
        tv_tool_title.text = getString(R.string.filter_by)
        minPriceS = intent?.getStringExtra(KEY_MIN_PRICE)
        maxPriceS = intent?.getStringExtra(KEY_MAX_PRICE)
        catList = intent.getParcelableArrayListExtra(KEY_CATEGORY_LIST)!!
        brandList = intent.getParcelableArrayListExtra(KEY_BRAND_LIST)!!

        categoryMultipleListModels = intent.getParcelableArrayListExtra(KEY_CAT_MODEL)!!
        brandMultipleListModels = intent.getParcelableArrayListExtra(KEY_BRAND_MODEL)!!

        initRangeBar()
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }

        rootFilters = Arrays.asList(*this.resources.getStringArray(R.array.filter_type))
        for (i in rootFilters!!.indices) {
            /* Create new MainFilterModel object and set array value to @model
             * Description:
             * -- Class: MainFilterModel.java
             * -- Package:main.shop.javaxerp.com.shoppingapp.model
             * */
            val model = MainFilterModel()
            /*Title for list item*/
            model.title = rootFilters!![i]
            /*Subtitle for list item*/
            model.setSub("All")
            /*Example:
             * --------------------------------------------
             * Brand => title
             * All => subtitle
             * --------------------------------------------
             * Color => title
             * All => subtitle
             * --------------------------------------------
             * */

            /*add MainFilterModel object @model to ArrayList*/
            filterModels.add(model)
        }

        adapter = FilterRecyclerAdapter(this, R.layout.item_filter_type, filterModels)
        filter_dialog_listview!!.adapter = adapter
        filter_dialog_listview!!.layoutManager = LinearLayoutManager(this)
        filter_dialog_listview!!.setHasFixedSize(true)

        filterValAdapter = FilterValRecyclerAdapter(
            this,
            R.layout.item_category_filter,
            categoryMultipleListModels,
            MainFilterModel.Model.CATEGORY
        )
        filter_value_listview!!.adapter = filterValAdapter
        filter_value_listview!!.layoutManager = LinearLayoutManager(this)
        filter_value_listview!!.setHasFixedSize(true)

        btn_filter!!.setOnClickListener {
            for (model in categoryMultipleListModels) {
                if (model.isChecked) {
                    filterModels[MainFilterModel.Model.INDEX_CATEGORY].subtitles.add(model.name)
                    filterModels[MainFilterModel.Model.INDEX_CATEGORY].id.add(model.id)

                }
            }

            for (model in priceMultipleListModels) {
                if (model.isChecked) {
                    filterModels[MainFilterModel.Model.INDEX_PRICE].subtitles.add(model.name)
                }
            }

            for (model in brandMultipleListModels) {
                if (model.isChecked) {
                    filterModels[MainFilterModel.Model.INDEX_BRAND].subtitles.add(model.name)
                    filterModels[MainFilterModel.Model.INDEX_BRAND].id.add(model.id)

                }

            }
            /*Get value from checked of category checkbox*/
            categorySelected = filterModels[MainFilterModel.Model.INDEX_CATEGORY].subtitles
            categorySelectedIds = filterModels[MainFilterModel.Model.INDEX_CATEGORY].id

            filterModels[MainFilterModel.Model.INDEX_CATEGORY].subtitles = ArrayList()
            filterModels[MainFilterModel.Model.INDEX_CATEGORY].id = ArrayList()

            /*Get value from checked of price checkbox*/
            brandSelected = filterModels[MainFilterModel.Model.INDEX_BRAND].subtitles
            brandSelectedIds = filterModels[MainFilterModel.Model.INDEX_BRAND].id

            filterModels[MainFilterModel.Model.INDEX_BRAND].subtitles = ArrayList()
            filterModels[MainFilterModel.Model.INDEX_BRAND].id = ArrayList()

            /*Get value from checked of price checkbox*/
            priceSelected = filterModels[MainFilterModel.Model.INDEX_PRICE].subtitles

            filterModels[MainFilterModel.Model.INDEX_PRICE].subtitles = ArrayList()

            if (categorySelected.isEmpty() && priceSelected.isEmpty() && brandSelected.isEmpty()) {
                Toast.makeText(
                    this@FilterActivity,
                    "Please select category,price,brand",
                    Toast.LENGTH_SHORT
                ).show()
            }

            /* if (!categorySelected.isEmpty() || !priceSelected.isEmpty() || !brandSelected.isEmpty()) {
                 Toast.makeText(
                     this@FilterActivity,
                     "Selected Size is $categorySelected\nSelected id is $categorySelectedIds\nSelected Brand is $brandSelectedIds",
                     Toast.LENGTH_LONG
                 ).show()
             }*/

            val intent = Intent()
            intent.putExtra("catModel", categoryMultipleListModels)
            intent.putExtra("catSelected", categorySelectedIds)
            intent.putExtra("brandModel", brandMultipleListModels)
            intent.putExtra("brandSelected", brandSelectedIds)
            intent.putExtra("minPrice", minPrice)
            intent.putExtra("maxPrice", maxPrice)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        /*TODO: Clear user selected */
        btn_clear!!.setOnClickListener {
            for (selectedModel in categoryMultipleListModels) {
                selectedModel.isChecked = false

            }

            for (selectedModel in brandMultipleListModels) {
                selectedModel.isChecked = false

            }

            for (selectedModel in priceMultipleListModels) {
                selectedModel.isChecked = false

            }
            adapter!!.notifyDataSetChanged()
            filterValAdapter!!.notifyDataSetChanged()
        }


        adapter!!.setOnItemClickListener(object : FilterRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                filterItemListClicked(position, v)
                adapter!!.setItemSelected(position)
            }
        })
        filterItemListClicked(0, null)
        adapter!!.setItemSelected(0)

        // categories = ArrayList(Arrays.asList(*resources.getStringArray(R.array.filter_category)))
        if (categoryMultipleListModels?.size == 0) {
            for (category in catList) {

                /* Create new FilterDefaultMultipleListModel object for brand and set array value to brand model {@model}
             * Description:
             * -- Class: FilterDefaultMultipleListModel.java
             * -- Package:main.shop.javaxerp.com.shoppingapp.model
             * NOTE: #checked value @FilterDefaultMultipleListModel is false;
             * */
                val model = FilterDefaultMultipleListModel()
                model.name = category?.name
                model.id = category.id
                model.id = category.id
                model.isChecked = category.isChecked
                /*add brand model @model to ArrayList*/
                categoryMultipleListModels.add(model)
            }
        }
        //   brands = ArrayList(Arrays.asList(*resources.getStringArray(R.array.filter_brands)))
        if (brandMultipleListModels?.size == 0) {
            for (brand in brandList) {

                /* Create new FilterDefaultMultipleListModel object for brand and set array value to brand model {@model}
             * Description:
             * -- Class: FilterDefaultMultipleListModel.java
             * -- Package:main.shop.javaxerp.com.shoppingapp.model
             * NOTE: #checked value @FilterDefaultMultipleListModel is false;
             * */
                val model = FilterDefaultMultipleListModel()
                model.name = brand?.name
                model.id = brand?.id

                /*add brand model @model to ArrayList*/
                brandMultipleListModels.add(model)
            }
        }
        prices = ArrayList(Arrays.asList(*resources.getStringArray(R.array.filter_category)))
        for (price in prices) {

            /* Create new FilterDefaultMultipleListModel object for brand and set array value to brand model {@model}
             * Description:
             * -- Class: FilterDefaultMultipleListModel.java
             * -- Package:main.shop.javaxerp.com.shoppingapp.model
             * NOTE: #checked value @FilterDefaultMultipleListModel is false;
             * */
            val model = FilterDefaultMultipleListModel()
            model.name = price

            /*add brand model @model to ArrayList*/
            priceMultipleListModels.add(model)
        }
    }


    private fun filterItemListClicked(position: Int, v: View?) {
        if (position == 0) {
            rlRange.visibility = View.GONE
            filter_value_listview.visibility = View.VISIBLE
            filterValAdapter = FilterValRecyclerAdapter(
                this,
                R.layout.item_category_filter,
                categoryMultipleListModels,
                MainFilterModel.Model.CATEGORY
            )
        } else if (position == 1) {
            rlRange.visibility = View.GONE
            filter_value_listview.visibility = View.VISIBLE
            filterValAdapter = FilterValRecyclerAdapter(
                this,
                R.layout.item_category_filter,
                brandMultipleListModels,
                MainFilterModel.Model.BRAND
            )
        } else {
            /*filterValAdapter = FilterValRecyclerAdapter(
                this,
                R.layout.item_category_filter,
                priceMultipleListModels,
                MainFilterModel.Model.PRICE
            )*/
            rlRange.visibility = View.VISIBLE
            filter_value_listview.visibility = View.GONE

        }

        filter_value_listview!!.adapter = filterValAdapter

        filterValAdapter!!.setOnItemClickListener(object :
            FilterValRecyclerAdapter.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                filterValitemListClicked(position)
            }
        })
        filterValAdapter!!.notifyDataSetChanged()
    }

    private fun filterValitemListClicked(position: Int) {
        filterValAdapter!!.setItemSelected(position)
    }

    companion object {
        const val KEY_CATEGORY_LIST = "KEY_CATEGORY_LIST"
        const val KEY_CAT_MODEL = "KEY_CAT_MODEL"
        const val KEY_BRAND_LIST = "KEY_BRAND_LIST"
        const val KEY_BRAND_MODEL = "KEY_BRAND_MODEL"
        const val KEY_MIN_PRICE = "KEY_MIN_PRICE"
        const val KEY_MAX_PRICE = "KEY_MAX_PRICE"

        fun getIntent(
            context: Context,
            catList: ArrayList<CategoriesItem>?,
            brandList: ArrayList<BrandItem>?,
            catModel: ArrayList<FilterDefaultMultipleListModel>,
            brandModel: ArrayList<FilterDefaultMultipleListModel>,
            minPrice: String,
            maxPrice: String
        ): Intent? {
            val intent = Intent(context, FilterActivity::class.java)
            intent.putParcelableArrayListExtra(KEY_CATEGORY_LIST, catList)
            intent.putParcelableArrayListExtra(KEY_CAT_MODEL, catModel)
            intent.putParcelableArrayListExtra(KEY_BRAND_LIST, brandList)
            intent.putParcelableArrayListExtra(KEY_BRAND_MODEL, brandModel)
            intent.putExtra(KEY_MIN_PRICE, minPrice)
            intent.putExtra(KEY_MAX_PRICE, maxPrice)

            return intent
        }
    }

    private fun initRangeBar() {
        rangeBar.onRangeChanged = { leftPinValue, rightPinValue ->
            rangeBarLeftValue.text =
                AndroidUtils.getString(R.string.price_type) + " " + leftPinValue.toString()
            rangeBarRightValue.text =
                AndroidUtils.getString(R.string.price_type) + " " + rightPinValue.toString()
            minPrice = leftPinValue
            maxPrice = rightPinValue

        }
        rangeBar.onLeftPinChanged = { index, leftPinValue ->
            Log.d(this.javaClass.canonicalName, "$index $leftPinValue")
        }
        rangeBar.onRightPinChanged = { index, rightPinValue ->
            Log.d(this.javaClass.canonicalName, "$index $rightPinValue")
        }
        rangeBar.onSelectedEntriesSizeChanged = { selectedEntriesSize ->
            Log.d(this.javaClass.canonicalName, "$selectedEntriesSize")
        }

        for (x in 0..200) {
            rangeBarEntries.add(BarEntry(x.toFloat(), x.toFloat()))
        }

        rangeBarLeftValue.text = AndroidUtils.getString(R.string.price_type) + " " + 0.toString()
        rangeBarRightValue.text = AndroidUtils.getString(R.string.price_type) + " " + 200.toString()
        rangeBar.setEntries(rangeBarEntries)
        rangeBar.setSelectedEntries(minPriceS!!.toInt(), maxPriceS!!.toInt())

    }

}
