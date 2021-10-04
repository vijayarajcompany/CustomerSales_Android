package com.pepsidrc.base

import android.content.Context
import com.pepsidrc.model.product.ItemMastersItem
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by AB on 8/29/2018.
 */

class StoreProducts
{

    companion object {

        private var INSTANCE: StoreProducts? = null

        fun getInstance(): StoreProducts {
            if (INSTANCE == null)  // NOT thread safe!
                INSTANCE = StoreProducts()

            return INSTANCE!!
        }

    }
    private val context: Context? = null
    internal var products = HashMap<Int, ItemMastersItem>()

    val listOfProducts: List<ItemMastersItem>
        get() = ArrayList<ItemMastersItem>(products.values)

    fun saveProducts(productList: List<ItemMastersItem>?) {
        if (productList != null) {
            for (i in productList.indices) {
                products.put(productList[i].id, productList[i])
            }
        }
    }

    fun getProduct(id: Int?): ItemMastersItem? {
        return products.get(id)
    }

    fun addProduct(allProduct: ItemMastersItem?) {
        if (allProduct != null) {
            products.put(allProduct?.id, allProduct)
        }
    }

    fun clear() {
        INSTANCE = null
    }

}
