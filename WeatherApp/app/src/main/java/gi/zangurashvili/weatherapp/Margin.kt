package gi.zangurashvili.weatherapp

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class Margin(val space: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = space
    }
}