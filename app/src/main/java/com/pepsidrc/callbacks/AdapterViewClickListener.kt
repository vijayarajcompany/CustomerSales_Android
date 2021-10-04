package com.pepsidrc.callbacks

interface AdapterViewClickListener<T> {

    fun onClickAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}