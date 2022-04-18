package com.example.focusongraphql.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.focusongraphql.FindContriesOfAContinentQuery
import com.example.focusongraphql.GetContinentsQuery
import com.example.focusongraphql.network.models.Continent
import com.example.focusongraphql.network.models.Country
import kotlin.random.Random

const val SELECTED_CODE = "SELECTED_CODE"
const val SELECTED_CONTINENT = "SELECTED_CONTINENT"

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.setRandomBackgroundColor() {
    var red = Random.nextInt(0, 256)
    var green = Random.nextInt(0, 256)
    var blue = Random.nextInt(0, 256)
    setBackgroundColor(Color.argb(100, red, green, blue))
}

fun convertToContinentDataModel(toList: List<GetContinentsQuery.Continent?>?): List<Continent> {
    val continentList = mutableListOf<Continent>()
    toList?.forEach { it ->
        continentList.add(Continent(it?.code ?: "", it?.name ?: ""))
    }
    return continentList
}

fun convertToCountryDataModel(toList: List<FindContriesOfAContinentQuery.Country?>?): List<Country> {
    val countriesList = mutableListOf<Country>()
    toList?.forEach { it ->
        countriesList.add(
            Country(
                it?.name ?: "",
                it?.native ?: "",
                it?.phone ?: "",
                it?.currency ?: "",
                it?.emoji ?: "",
            )
        )
    }
    return countriesList
}

fun AppCompatActivity.makeStatusBarTransparentAndContentFullScreen() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}