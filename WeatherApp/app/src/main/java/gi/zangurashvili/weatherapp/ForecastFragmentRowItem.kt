package gi.zangurashvili.weatherapp

import android.media.Image

data class ForecastFragmentRowItem(
    val date: String,
    val icon: String,
    val temperature: String,
    val description: String
)
