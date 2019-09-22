package safari.com.iltall.UI.Network


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import safari.com.iltall.Data.Adapter.FollowAdapter
import safari.com.iltall.Data.Adapter.RankAdapter
import safari.com.iltall.Data.Dataclass.Follow
import safari.com.iltall.Data.Dataclass.Rank

import safari.com.iltall.R

/**
 * A simple [Fragment] subclass.
 */
class RankFragment : Fragment() {
    lateinit var rankList:ArrayList<Rank>
    lateinit var adapter: RankAdapter
    lateinit var rView: RecyclerView
    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_rank, container, false)
        init()
        return v
    }
    fun init(){
        initData()
        initLayout()
    }
    fun initData(){
        rankList = arrayListOf()
        rankList.add(Rank("",2,"han2258",15,20))
        rankList.add(Rank("",1,"sejeong",100,30))
    }
    fun initLayout(){
        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        rView = v!!.findViewById(R.id.rk_rView)
        rView.layoutManager = layoutManager
        adapter = RankAdapter(rankList, context!!)
        rView.adapter = adapter
    }
}
