package com.pepsidrc.ui.navigation.ui.shop

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pepsidrc.R
import com.pepsidrc.common.CommonBoolean
import com.pepsidrc.interfaces.SortImpl
import com.pepsidrc.utils.Config
import kotlinx.android.synthetic.main.item_product.*
import kotlinx.android.synthetic.main.sortby_bottomsheetlayout.*
import org.jetbrains.anko.find

class SortByBottomSheet(activity: Activity,var sortImpl: SortImpl, var sort_by: String) : BottomSheetDialogFragment() {

    private lateinit var radioPopularity: RadioButton
    private lateinit var radioPrice_high_low: RadioButton
    private lateinit var radioPrice_low_high: RadioButton
    private lateinit var radioRating: RadioButton
    private lateinit var radioPromotions: RadioButton
    private lateinit var sortRadioGroup: RadioGroup
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)

    }
    @Nullable
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sortby_bottomsheetlayout, container, CommonBoolean.FALSE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioPopularity = view.find(R.id.radioPopularity)
        radioPrice_high_low = view.find(R.id.radioPrice_high_low)
        radioPrice_low_high = view.find(R.id.radioPrice_low_high)
        radioRating = view.find(R.id.radioRating)
        radioPromotions = view.find(R.id.radioPromotions)
        btnCancel = view.find(R.id.btnCancel)

        sortRadioGroup = view.find(R.id.radio_group)


        if(sort_by.equals(Config.Constants.asc)){

            radioPrice_low_high.isChecked=true
            radioPrice_high_low.isChecked=false
            radioRating.isChecked=false
            radioPromotions.isChecked=false

        }else if(sort_by.equals(Config.Constants.desc)){
            radioPrice_high_low.isChecked=true

            radioPrice_low_high.isChecked=false
            radioRating.isChecked=false
            radioPromotions.isChecked=false


        }else if(sort_by.equals(Config.Constants.atoz)){
            radioRating.isChecked=true

            radioPrice_high_low.isChecked=false

            radioPrice_low_high.isChecked=false
            radioPromotions.isChecked=false

        }else if(sort_by.equals(Config.Constants.ztoa)){
            radioPromotions.isChecked=true

            radioPrice_high_low.isChecked=false

            radioPrice_low_high.isChecked=false
            radioRating.isChecked=false
        }
        sortRadioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = view.findViewById(checkedId)
                /*Toast.makeText(context," On checked change : ${radio.tag}",
                    Toast.LENGTH_SHORT).show()*/
                sortImpl.sortBy(radio?.tag.toString())
                dismiss()
            })

            btnCancel.setOnClickListener { dismiss() }
    }



   /* @SuppressLint("SetTextI18n")
    fun selectSortOption(currentSize: Int, max: Int, progress: Int) {

        progressbar.progress = progress
        percentCount.text = "$progress%"
        statusCount.text = "$currentSize/$max"
        val string = getString(R.string.uploading_the_data_for_n_school_s, max)
        size.text = setBoldSpannable(string, max)

    }
*/

    companion object {
        const val TAG = "UploadProgessBottomSheet"
    }


}
