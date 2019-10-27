package safari.com.iltall.UI

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_status.*
import safari.com.iltall.Data.Adapter.TitleAdapter
import safari.com.iltall.Data.Dataclass.Follow
import safari.com.iltall.Data.Dataclass.Title
import safari.com.iltall.R

class StatusActivity : AppCompatActivity() {
    
    lateinit var status: Follow
    lateinit var titleList:ArrayList<Title>
    lateinit var adapter: TitleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        init()
    }
    fun init(){
        initData()
        initLayout()
        initListener()
    }
    fun initData() {
        var intent = getIntent()
        status = intent.getSerializableExtra("user") as Follow
        titleList = status.titleList
    }
    fun initLayout(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        st_rView.layoutManager = layoutManager
        adapter = TitleAdapter(titleList, this)
        st_rView.adapter = adapter
        if(status.img.isNotEmpty())
            st_img.setImageBitmap(BitmapFactory.decodeFile(status.img))
        st_nickname.text = status.id
        st_follower.text = "팔로워 "+status.follower.toString()
        st_following.text = "팔로잉 "+status.following.toString()
        /*
        st_gave.text = "낸 문제 : " + status.gave + "개"
        st_solved.text = "푼 문제 : " + status.solved +"개"
         */
    }
    fun initListener(){
        st_menu.setOnClickListener {
            val popup = PopupMenu(this,it)
            menuInflater.inflate(R.menu.st_menu,popup.menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.setting ->
                        Toast.makeText(this@StatusActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.store->
                        Toast.makeText(this@StatusActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.dev_info->
                        Toast.makeText(this@StatusActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                }
                true
            })
            popup.show()
        }
    }
    
}
