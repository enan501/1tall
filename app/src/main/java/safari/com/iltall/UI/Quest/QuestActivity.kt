package safari.com.iltall.UI.Quest

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quest.*
import safari.com.iltall.Data.Adapter.QuestAdapter
import safari.com.iltall.Data.Dataclass.Quest
import safari.com.iltall.R

class QuestActivity : AppCompatActivity() {

    val pageNum = 3
    lateinit var answer:String
    lateinit var adapter:FragmentPagerAdapter
    lateinit var quest:Quest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest)
        init()
    }
    fun init(){
        initData()
        initLayout()
        initListener()
    }
    fun initData(){
        var intent = getIntent()
        quest = intent.getSerializableExtra("PLAY") as Quest

        qt_title.text = quest.title
        qt_hint.text = quest.hint
        answer =quest.answer

    }
    fun initLayout(){
        adapter = QuestAdapter(supportFragmentManager,quest.content)
        qt_pager.adapter = adapter
    }
    fun initListener(){
        see_ad.setOnClickListener {
            qt_hint.visibility = View.VISIBLE
            see_ad.visibility = View.GONE
            Toast.makeText(this@QuestActivity,"광고 준비중입니다",Toast.LENGTH_SHORT).show()
        }
        qt_submit.setOnClickListener {
            var mA = qt_answer.text.toString()
            if(quest.answer == mA){
                Toast.makeText(this,"정답입니다",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
