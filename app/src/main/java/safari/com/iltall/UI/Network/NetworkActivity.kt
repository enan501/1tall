package safari.com.iltall.UI.Network

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_network.*
import safari.com.iltall.Data.Adapter.TabAdapter
import safari.com.iltall.R

class NetworkActivity : AppCompatActivity() {
    lateinit var adapter: TabAdapter
    var tabPos: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
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
        nw_tab.addTab(nw_tab.newTab().setText("피드"))
        nw_tab.addTab(nw_tab.newTab().setText("팔로우"))
        nw_tab.addTab(nw_tab.newTab().setText("랭킹"))
    }
    fun initListener(){
        adapter = TabAdapter(supportFragmentManager, nw_tab.tabCount)
        nw_viewPager.adapter = adapter


    }

}
