package safari.com.iltall.UI.Radar

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.quiz_ballon.view.*
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import safari.com.iltall.Data.Dataclass.Quest
import safari.com.iltall.R
import safari.com.iltall.UI.Quest.QuestListActivity

class CustomQuiz:CalloutBalloonAdapter{

    lateinit var mCBallon:View
    lateinit var quiz:ArrayList<Quest>
    lateinit var context: Context
    constructor(context:Context){
        var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        quiz = arrayListOf()
        this.context = context
        mCBallon = inflater.inflate(R.layout.quiz_ballon,null)
    }

    fun setData(quiz:ArrayList<Quest>){
        this.quiz = quiz
    }


    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View? {
        return mCBallon
    }

    override fun getCalloutBalloon(p0: MapPOIItem?): View {
        var q:Quest? = null
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