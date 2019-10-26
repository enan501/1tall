package safari.com.iltall.UI.Network

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_network.*
import org.w3c.dom.Text
import safari.com.iltall.Data.Adapter.TabAdapter
import safari.com.iltall.R

class NetworkActivity : AppCompatActivity() {
    lateinit var adapter: TabAdapter
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
        nw_tab.setupWithViewPager(nw_viewPager)
        nw_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                nw_viewPager.currentItem = tab.position
            }
        })

    }

}
