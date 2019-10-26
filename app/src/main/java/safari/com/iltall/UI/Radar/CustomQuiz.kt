package safari.com.iltall.UI.Radar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.quiz_ballon.view.*
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import safari.com.iltall.R

class CustomQuiz:CalloutBalloonAdapter{

    lateinit var mCBallon:View

    constructor(context:Context){
        var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mCBallon = inflater.inflate(R.layout.quiz_ballon,null)
    }


    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View? {
        return mCBallon
    }

    override fun getCalloutBalloon(p0: MapPOIItem?): View {
       // mCBallon.quiz_image.setImageResource()
        Log.d("말풍선","떠라")
        mCBallon.quiz_text.text="문제입ㄴ디ㅏ"
        return mCBallon
    }
}