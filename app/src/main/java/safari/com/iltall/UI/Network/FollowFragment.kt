package safari.com.iltall.UI.Network


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import safari.com.iltall.Data.Adapter.FollowAdapter
import safari.com.iltall.Data.Dataclass.Follow
import safari.com.iltall.R

/**
 * A simple [Fragment] subclass.
 */
class FollowFragment : Fragment() {
    lateinit var followList:ArrayList<Follow>
    lateinit var adapter: FollowAdapter
    lateinit var rView: RecyclerView
    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.fragment_follow, container, false)
        init()
        return v
    }
    fun init(){
        initData()
        initLayout()
    }
    fun initData(){
        followList = arrayListOf()
        followList.add(Follow("","sejeong",100,30))
        followList.add(Follow("","han2258",15,20))
    }
    fun initLayout(){
        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        rView = v!!.findViewById(R.id.fw_rView)
        rView.layoutManager = layoutManager
        adapter = FollowAdapter(followList, context!!)
        rView.adapter = adapter
    }

}
