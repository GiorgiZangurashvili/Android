package gi.zangurashvili.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import gi.zangurashvili.weatherapp.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewpager
        viewPager.adapter = ViewPager2Adapter(this)

        binding.weatherButton.setOnClickListener {
            binding.viewpager.setCurrentItem(0, true)
        }
        binding.forecastButton.setOnClickListener {
            binding.viewpager.setCurrentItem(1, true)
        }
    }
}