package safari.com.iltall.UI.Quest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import safari.com.iltall.Data.Adapter.QuestListAdapter
import safari.com.iltall.Data.Dataclass.Quest
import safari.com.iltall.R

class QuestListActivity : AppCompatActivity() {
    lateinit var questList:ArrayList<Quest>
    lateinit var adapter: QuestListAdapter
    lateinit var rView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_list)
        init()
    }
    fun init(){
        initData()
        initLayout()
    }
    fun initData(){
        questList = arrayListOf()
        var intent = intent
        questList = intent.getSerializableExtra("QUIZ") as ArrayList<Quest>
        /*
        questList.add(Quest("건덕이 실종사건","개미핥기",153,100,501,false))
        questList.add(Quest("살려주세요","세정대왕",402,315,332))
        questList.add(Quest("집에가고싶다","고래",115,117,321))
        questList.add(Quest("코딩그만할래","여우",2,10,15))
        questList.add(Quest("제2학관 감금사건","원숭이",0,12,30,false))
        questList.add(Quest("고양이가 사라졌다","다람쥐",1,2,4,false))
        questList.add(Quest("도망자 개미핥기","코끼리",53,115,222))
        questList.add(Quest("탈주","개미핥기",234,998,1022))
         */
    }
    fun initLayout(){
        val layoutManager =GridLayoutManager(this, 3)
        rView = findViewById(R.id.ql_rView)
        rView.layoutManager = layoutManager
        adapter = QuestListAdapter(questList,this)
        rView.adapter = adapter

        adapter.itemClickListener = object:QuestListAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: QuestListAdapter.ViewHolder,
                data: Quest,
                position: Int
            ) {
//                val builder = AlertDialog.Builder(this@QuestListActivity) //alert 다이얼로그 builder 이용해서 다이얼로그 생성
//                val questDialog = layoutInflater.inflate(R.layout.dialog_quest_list, null)
//                builder.setView(questDialog)

                val nextIntent = Intent(this@QuestListActivity, QuestActivity::class.java)
                nextIntent.putExtra("PLAY",data)
                startActivity(nextIntent)
            }

        }
    }
}
