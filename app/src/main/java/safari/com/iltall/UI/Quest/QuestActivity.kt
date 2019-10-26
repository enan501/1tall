package safari.com.iltall.UI.Quest

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quest.*
import safari.com.iltall.R

class QuestActivity : AppCompatActivity() {

    val pageNum = 3
    lateinit var answer:String
    lateinit var adapter:FragmentPagerAdapter
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
        qt_title.text = intent.getStringExtra("title")
        qt_text.text = intent.getStringExtra("text")
        qt_hint.text = intent.getStringExtra("hint")
        answer = intent.getStringExtra("answer")
    }
    fun initLayout(){
        //adapter = QuestAdapter(supportFragmentManager,pageNum)
        //qt_pager.adapter = adapter
    }
    fun initListener(){
        see_ad.setOnClickListener {
            qt_hint.visibility = View.VISIBLE
            see_ad.visibility = View.GONE
            Toast.makeText(this@QuestActivity,"광고 준비중입니다",Toast.LENGTH_SHORT).show()
        }
    }

}
