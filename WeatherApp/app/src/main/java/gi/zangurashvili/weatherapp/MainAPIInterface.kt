package gi.zangurashvili.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPIInterface {

    @GET("data/2.5/weather")
    fun getData(
        @Query("q") city: String,
        @Query("appid") key: String,
        @Query("units") units: String = "metric"
    ): Call<WeatherData>
}