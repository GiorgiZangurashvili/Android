package gi.zangurashvili.weatherapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gi.zangurashvili.weatherapp.databinding.FragmentWeatherBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset
import androidx.core.content.ContextCompat


class WeatherFragment : Fragment() {
    val BASE_URL = "https://api.openweathermap.org/"
    val API_KEY = "1c6957a777dce0558ec8993bb540462e"
    val ICON_URL_START = "https://openweathermap.org/img/wn/"
    val ICON_URL_END = "@2x.png"
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var recyclerView: RecyclerView

    private fun getMainAndTimeData(city: String, callback: (ArrayList<Int>) -> Unit){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MainAPIInterface::class.java)


        val weatherData = retrofitBuilder.getData(city, API_KEY)
        weatherData.enqueue(object: Callback<WeatherData>{
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if(response.isSuccessful){
                    val mainData = response.body()?.main!!
                    val list = ArrayList<Int>()
                    list.add(mainData.temp.toInt())
                    list.add(mainData.feels_like.toInt())
                    list.add(mainData.humidity)
                    list.add(mainData.pressure)
                    val timeData = response.body()!!
                    list.add(timeData.dt)
                    list.add(timeData.timezone)
                    callback(list)
                }else{

                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getWeatherData(city: String, callback: (ArrayList<String>) -> Unit){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MainAPIInterface::class.java)


        val weatherData = retrofitBuilder.getData(city, API_KEY)
        weatherData.enqueue(object: Callback<WeatherData>{
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if(response.isSuccessful){
                    val weatherData = response.body()?.weather!!
                    val list = ArrayList<String>()
                    list.add(weatherData[0].icon)
                    list.add(weatherData[0].description)
                    callback(list)
                }else{

                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val listener = View.OnClickListener { v ->
        var city: String = ""
        if(v.id == binding.include.georgia.id){
            city = "tbilisi"
        }else if(v.id == binding.include.uk.id){
            city = "london"
        }else if(v.id == binding.include.jamaica.id){
            city = "kingston"
        }

        getMainAndTimeData(city){ list ->
            val data = arrayListOf<WeatherFragmentRowItem>(
                WeatherFragmentRowItem("Temperature", list[0].toString() + "°"),
                WeatherFragmentRowItem("Feels Like", list[1].toString() + "°"),
                WeatherFragmentRowItem("Humidity", list[2].toString()),
                WeatherFragmentRowItem("Pressure", list[3].toString())
            )
            val adapter = WeatherFragmentRecyclerView(data)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.include.cityName.text = city.uppercase()
            binding.bigTemp.text = data[0].value
            val dt = list[4]
            val timezone = list[5]
            if(isDayTime(dt, timezone)){
                val color = activity?.let { ContextCompat.getColor(it, R.color.day) }
                binding.weatherLayout.setBackgroundColor(color!!)
            }else{
                val color = activity?.let { ContextCompat.getColor(it, R.color.night) }
                binding.weatherLayout.setBackgroundColor(color!!)
            }
        }

        getWeatherData(city){ list ->
            binding.weatherDescription.text = list[1].uppercase()
            val iconName = list[0]
            val url = ICON_URL_START + iconName + ICON_URL_END
            activity?.let { Glide.with(it).load(url).into(binding.icon) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(){
        initRecyclerView()
        setOnClickListeners()
        binding.include.georgia.performClick()
    }

    private fun initRecyclerView(){
        recyclerView = binding.weatherRecyclerView
        recyclerView.addItemDecoration(Margin(resources.getDimensionPixelSize(R.dimen.low_space)))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setOnClickListeners(){
        binding.include.georgia.setOnClickListener(listener)
        binding.include.uk.setOnClickListener(listener)
        binding.include.jamaica.setOnClickListener(listener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isDayTime(dt: Int, timezone: Int): Boolean{
        val time = Instant.ofEpochSecond(dt.toLong()).atZone(ZoneOffset.ofTotalSeconds(timezone))
        val local = time.toLocalTime()
        val dayStart = LocalTime.of(6, 0)
        val dayEnd = LocalTime.of(18, 0)
        return (dayStart.isBefore(local) && dayEnd.isAfter(local))
    }
}