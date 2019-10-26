package safari.com.iltall.UI.Quest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_make_quest.*
import safari.com.iltall.R

class MakeQuestActivity : AppCompatActivity() {

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
    }
    private fun initLayout() {

    }
    private fun initListener() {
        mq_submit.setOnClickListener {
            var intent = Intent()
            intent.putExtra("title",mq_title.text)
            intent.putExtra("text",mq_text.text)
            intent.putExtra("hint",mq_hint.text)
            intent.putExtra("answer",mq_answer.text)
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}
