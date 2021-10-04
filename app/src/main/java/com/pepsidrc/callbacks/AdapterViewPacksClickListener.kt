package com.pepsidrc.callbacks

interface AdapterViewPacksClickListener<T> {

    fun onClickPacksAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}