package gi.zangurashvili.assn1

import android.annotation.SuppressLint
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.coroutines.delay
import java.util.Collections

class MainActivity : AppCompatActivity() {

    var numCorrect = 0
    var numFalse = 0
    var prevId: Int = -1
    var prevImage: Int = -1
    var imageList = ArrayList<Int>()
    var imageButtonList = ArrayList<Int>()
    var imageButtonMap = HashMap<Int, Int>()
    val backOfCard = R.drawable.img_5

    fun init(){
        imageList.clear()
        fillImageList(imageList)
        prevId = -1
        numCorrect = 0
        numFalse = 0
        prevImage = -1
        val tw:TextView = findViewById(R.id.TextView1)
        tw.setText(R.string.success)
        tw.setTextColor(getColor(R.color.gray))
        val tw2:TextView = findViewById(R.id.TextView2)
        tw2.setText(R.string.fail)
        tw2.setTextColor(getColor(R.color.gray))
        for (imageButton in imageButtonList){
            val imgButton = findViewById<ImageButton>(imageButton)
            imgButton.setImageResource(backOfCard)
            imgButton.visibility = View.VISIBLE
        }
        makeViewsClickable(true)
    }

    val listener = View.OnClickListener { v ->
        if(v.id == prevId)
            return@OnClickListener

        makeViewsClickable(true)
        val imagebutton = v as ImageButton
        val imageIndex = imageButtonMap[v.id]
        if (imageIndex != null) {
            imagebutton.setImageResource(imageList[imageIndex - 1])
        }else{
            return@OnClickListener
        }

        if(prevId == -1){
            prevId = v.id
            prevImage = imageList[imageIndex - 1]
        }else if(prevId != -1 && prevId != v.id){
            makeViewsClickable(false)
            if(prevImage == imageList[imageIndex - 1]){
                numCorrect++
                val sub:String = getSubString(numCorrect, R.string.success)
                v.postDelayed({
                    val tw:TextView = findViewById(R.id.TextView1)
                    tw.setTextColor(getColor(R.color.green))
                    tw.text = sub
                    v.visibility = View.INVISIBLE
                    findViewById<ImageButton>(prevId).visibility = View.INVISIBLE
                    makeViewsClickable(true)
                    prevId = -1
                }, 1000)
            }else{
                numFalse++
                val sub: String = getSubString(numFalse, R.string.fail)
                v.postDelayed({
                    val tw:TextView = findViewById(R.id.TextView2)
                    tw.setTextColor(getColor(R.color.red))
                    tw.text = sub
                    v.setImageResource(backOfCard)
                    findViewById<ImageButton>(prevId).setImageResource(backOfCard)
                    makeViewsClickable(true)
                    prevId = -1
                }, 1000)
            }

        }
    }

    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fillImageButtonList(imageButtonList)

        fillImageMap(imageButtonMap)
        fillImageList(imageList)

        for (imageButton in imageButtonList){
            findViewById<ImageButton>(imageButton).setOnClickListener(listener)
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            init()
        }
    }

    fun fillImageList(imageList: ArrayList<Int>){
        imageList.add(R.drawable.img_1)
        imageList.add(R.drawable.img)
        imageList.add(R.drawable.img_2)
        imageList.add(R.drawable.img_1)
        imageList.add(R.drawable.img_2)
        imageList.add(R.drawable.img)
        imageList.shuffle()
    }

    fun fillImageMap(imageMap: HashMap<Int, Int>){
        imageMap.put(R.id.imageButton1, 1)
        imageMap.put(R.id.imageButton2, 2)
        imageMap.put(R.id.imageButton3, 3)
        imageMap.put(R.id.imageButton4, 4)
        imageMap.put(R.id.imageButton5, 5)
        imageMap.put(R.id.imageButton6, 6)
    }

    fun fillImageButtonList(imageButtonList: ArrayList<Int>){
        imageButtonList.add(R.id.imageButton1)
        imageButtonList.add(R.id.imageButton2)
        imageButtonList.add(R.id.imageButton3)
        imageButtonList.add(R.id.imageButton4)
        imageButtonList.add(R.id.imageButton5)
        imageButtonList.add(R.id.imageButton6)
    }

    fun makeViewsClickable(clickable: Boolean){
        for (imageButton in imageButtonList){
            val imgButton = findViewById<ImageButton>(imageButton)
            if(imgButton.visibility == View.VISIBLE){
                imgButton.isClickable = clickable
            }
        }
    }

    fun getSubString(num: Int, resource: Int): String{
        val newSuccess:String = getString(resource)
        var sub:String = newSuccess.substring(0, newSuccess.length - 1)
        sub += "" + num
        return sub
    }
}