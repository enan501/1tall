package safari.com.iltall.UI.Network

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import safari.com.iltall.Data.Adapter.FeedAdapter
import safari.com.iltall.Data.Dataclass.Feed
import safari.com.iltall.R

class FeedFragment : Fragment() {

    lateinit var feedList:ArrayList<Feed>
    lateinit var adapter: FeedAdapter
    lateinit var rView: RecyclerView
    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_feed, container, false)
        init()
        return v
    }
    fun init(){
        initData()
        initLayout()
    }
    fun initData(){
        feedList = arrayListOf()
        feedList.add(Feed("han2258","이야아아아","2018-09-22","","han2258님이 200문제를 푸셨습니다"))
        feedList.add(Feed("sejeong","2222222야아ㅏㅏ","2018-09-23","","sejeong님이 100문제를 만들었습니다"))
    }
    fun initLayout(){
        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        rView = v!!.findViewById(R.id.fd_rView)
        rView.layoutManager = layoutManager
        adapter = FeedAdapter(feedList, context!!)
        rView.adapter = adapter

    }
}
