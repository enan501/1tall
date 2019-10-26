package safari.com.iltall.UI.Quest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.LinearSnapHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_make_quest.*
import safari.com.iltall.Data.Adapter.CenterZoomLayoutManager
import safari.com.iltall.Data.Adapter.QuestContentAdapter
import safari.com.iltall.Data.Dataclass.QuestContent
import safari.com.iltall.R

class MakeQuestActivity : AppCompatActivity() {
    var snapHelper = LinearSnapHelper()
    lateinit var contentList:ArrayList<QuestContent>
    lateinit var adapter: QuestContentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_quest)
        init()
    }
    fun init(){
        initData()
        initLayout()
        initListener()
    }


    private fun initData() {
        contentList = arrayListOf()
        contentList.add(QuestContent("",0,2))
        contentList.add(QuestContent("",0,2))
        contentList.add(QuestContent("",0,0))
        contentList.add(QuestContent("",0,1))
        contentList.add(QuestContent("",0,2))
    }
    private fun initLayout() {
        val layoutManager = CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        val smoothScroller = object : LinearSmoothScroller(this) {
            override fun getHorizontalSnapPreference(): Int {
                return SNAP_TO_END
            }
        }
        mq_rView.layoutManager = layoutManager
        snapHelper.attachToRecyclerView(mq_rView) //아이템 가운데로 끌어 맞추기
        adapter = QuestContentAdapter(contentList,this)
        mq_rView.adapter = adapter
        smoothScroller.targetPosition = 2
        smoothScroller.computeScrollVectorForPosition(2)
        contentList.removeAt(0)
        layoutManager.startSmoothScroll(smoothScroller)
        adapter.notifyDataSetChanged()
    }
    private fun initListener() {
        adapter.itemClickListener = object : QuestContentAdapter.OnItemClickListener {
            override fun OnItemClick(holder: QuestContentAdapter.ViewHolder, data: QuestContent, position: Int) {

                if(data.isAddLast==1){ //추가
                    contentList.add(position,QuestContent("",0,0))
                    adapter.notifyDataSetChanged()
                }
            }
        }
        mq_hint_btn.setOnClickListener {
            mq_hint_btn.visibility = View.GONE
            mq_hint_block.visibility = View.VISIBLE
        }
        mq_submit.setOnClickListener {
            var intent = Intent()
            intent.putExtra("title",mq_title.text)
            //intent.putExtra("text",mq_text.text)
            intent.putExtra("hint",mq_hint.text)
            intent.putExtra("answer",mq_answer.text)
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}
