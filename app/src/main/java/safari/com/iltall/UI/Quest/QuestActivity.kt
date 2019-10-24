package safari.com.iltall.UI.Quest

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quest.*
import safari.com.iltall.Data.Adapter.QuestAdapter
import safari.com.iltall.R

class QuestActivity : AppCompatActivity() {

    val pageNum = 3
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

    }
    fun initLayout(){
        adapter = QuestAdapter(supportFragmentManager,pageNum)
        qt_pager.adapter = adapter
    }
    fun initListener(){
        see_ad.setOnClickListener {
            qt_hint.visibility = View.VISIBLE
            see_ad.visibility = View.GONE
            Toast.makeText(this@QuestActivity,"광고 준비중입니다",Toast.LENGTH_SHORT).show()
        }
    }

}
