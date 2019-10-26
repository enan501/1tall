package safari.com.iltall.UI.Radar

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.X
import android.widget.Toast
import kotlinx.android.synthetic.main.quiz_ballon.view.*
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import safari.com.iltall.Data.Dataclass.MyLocation
import safari.com.iltall.Data.Dataclass.Quest
import safari.com.iltall.R
import safari.com.iltall.UI.Quest.QuestActivity
import safari.com.iltall.UI.Quest.QuestListActivity

class CustomQuiz:CalloutBalloonAdapter{

    lateinit var mCBallon:View
    lateinit var quiz:ArrayList<Quest>
    lateinit var context: Context
    lateinit var lo:MyLocation
    var q:Quest? = null
    constructor(context:Context,location: MyLocation){
        var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        quiz = arrayListOf()
        this.context = context
        this.lo = location
        mCBallon = inflater.inflate(R.layout.quiz_ballon,null)
    }

    fun setData(quiz:ArrayList<Quest>){
        this.quiz = quiz
    }


    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View? {
        var x = (Math.cos(lo!!.latitude)*6400*2*3.14/360)*Math.abs(lo!!.longitude-q!!.location.longitude)
        var y = 111*Math.abs(lo!!.latitude-q!!.location.latitude)
        var distance = Math.sqrt(x*x+y*y)
        if(distance <=30){
            val nextIntent = Intent(context, QuestActivity::class.java)
            nextIntent.putExtra("PLAY",q)
            startActivity(context,nextIntent,null)
        }else{
            Log.d("거리","10M이내아님")
        }
        return mCBallon
    }

    override fun getCalloutBalloon(p0: MapPOIItem?): View {
        for (i in 0..quiz.size-1) {
            if (p0!!.itemName == quiz[i].title) {
                mCBallon.quiz_title.text = quiz[i].title
                mCBallon.user_name.text = quiz[i].author
                q = quiz[i]
            }
        }
        return mCBallon
    }
}