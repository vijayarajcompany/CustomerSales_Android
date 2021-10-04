package com.jesualex.autocompletelocation

import com.google.android.libraries.places.api.model.AutocompletePrediction

/**
 * Created by jesualex on 01-03-19.
 */
interface AutocompleteLocationListener {
    fun onTextClear()

    fun onItemSelected(selectedPlace: AutocompletePrediction?)
}
