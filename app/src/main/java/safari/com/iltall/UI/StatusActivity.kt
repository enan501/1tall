package safari.com.iltall.UI

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_status.*
import safari.com.iltall.Data.Adapter.TitleAdapter
import safari.com.iltall.Data.Dataclass.Title
import safari.com.iltall.R

class StatusActivity : AppCompatActivity() {

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
    fun initData(){
        titleList = arrayListOf()
        titleList.add(Title("숙련된 탐정",""))
        titleList.add(Title("묻고 더블로가!",""))
        titleList.add(Title("행운아",""))
        titleList.add(Title("문제의 제왕",""))
        titleList.add(Title("스핑크스",""))
    }
    fun initLayout(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        st_rView.layoutManager = layoutManager
        adapter = TitleAdapter(titleList, this)
        st_rView.adapter = adapter
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
