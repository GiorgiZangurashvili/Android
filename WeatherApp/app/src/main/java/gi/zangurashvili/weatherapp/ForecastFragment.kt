package gi.zangurashvili.weatherapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gi.zangurashvili.weatherapp.databinding.FragmentForecastBinding
import gi.zangurashvili.weatherapp.databinding.FragmentWeatherBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastFragment : Fragment() {
    val BASE_URL = "https://api.openweathermap.org/"
    val API_KEY = "1c6957a777dce0558ec8993bb540462e"
    val ICON_URL_START = "https://openweathermap.org/img/wn/"
    val ICON_URL_END = "@2x.png"
    private lateinit var binding: FragmentForecastBinding
    private lateinit var recyclerView: RecyclerView

    private fun getData(city: String, callback: (ArrayList<ForecastFragmentRowItem>) -> Unit){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ForecastAPIInterface::class.java)


        val weatherData = retrofitBuilder.getData(city, API_KEY)
        weatherData.enqueue(object: Callback<WeatherDataList> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<WeatherDataList>, response: Response<WeatherDataList>) {
                if(response.isSuccessful){
                    val list = ArrayList<ForecastFragmentRowItem>()
                    val mainData = response.body()!!
                    val timezone = mainData.list[0].timezone
                    for(data in mainData.list){
                        val date = LocalDateTime.ofInstant(Instant.ofEpochSecond((data.dt + timezone).toLong()), ZoneId.systemDefault())
                        val dateInFormat = DateTimeFormatter.ofPattern("hh a dd MMMM").format(date)

                        val icon = ICON_URL_START + data.weather[0].icon + ICON_URL_END
                        val description = data.weather[0].description
                        val temperature: String = data.main.temp.toInt().toString() + "°"

                        list.add(ForecastFragmentRowItem(dateInFormat, icon, temperature, description))
                    }
                    callback(list)
                }else{
                    callback(ArrayList())
                }
            }

            override fun onFailure(call: Call<WeatherDataList>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                callback(ArrayList())
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForecastBinding.inflate(inflater, container, false)
        init()

        return binding.root
    }


    private fun init(){
        initRecyclerView()
        setOnClickListeners()
        binding.includeForecast.georgia.performClick()
    }

    private fun initRecyclerView(){
        recyclerView = binding.forecastRecyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL))
    }

    private fun setOnClickListeners(){
        binding.includeForecast.georgia.setOnClickListener(listener)
        binding.includeForecast.uk.setOnClickListener(listener)
        binding.includeForecast.jamaica.setOnClickListener(listener)
    }

    val listener = View.OnClickListener { v ->
        var city: String = ""
        if(v.id == binding.includeForecast.georgia.id){
            city = "tbilisi"
        }else if(v.id == binding.includeForecast.uk.id){
            city = "london"
        }else if(v.id == binding.includeForecast.jamaica.id){
            city = "kingston"
        }

        getData(city){ list ->
            val adapter = ForecastFragmentRecyclerView(list)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.includeForecast.cityName.text = city.uppercase()
        }
    }
}