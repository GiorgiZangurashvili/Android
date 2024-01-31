package gi.zangurashvili.weatherapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val fragments = listOf(WeatherFragment(), ForecastFragment())

    override fun getItemCount(): Int{
       return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}