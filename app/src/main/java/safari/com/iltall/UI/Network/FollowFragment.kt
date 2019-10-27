package safari.com.iltall.UI.Network


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import safari.com.iltall.Data.Adapter.FollowAdapter
import safari.com.iltall.Data.Dataclass.Follow
import safari.com.iltall.Data.Dataclass.Title
import safari.com.iltall.R
import safari.com.iltall.UI.StatusActivity

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
        initListener()
    }

    private fun initListener() {
        adapter.itemClickListener = object: FollowAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: FollowAdapter.ViewHolder,
                data: Follow,
                position: Int
            ) {
//                val builder = AlertDialog.Builder(this@QuestListActivity) //alert 다이얼로그 builder 이용해서 다이얼로그 생성
//                val questDialog = layoutInflater.inflate(R.layout.dialog_quest_list, null)
//                builder.setView(questDialog)
                val nextIntent = Intent(context, StatusActivity::class.java)
                nextIntent.putExtra("user",data)
                startActivity(nextIntent)
            }

        }
    }

    fun initData(){
        followList = arrayListOf()
        followList.add(Follow("","han2258",15,20, "건잘알", 55, 520, 180, 2, arrayListOf(Title("건잘알",""), Title( "행운의 탐정", ""), Title("고양이 매니아",""))))
        followList.add(Follow("","sejeong",100,30,"견습 탐정",15,130,127,1, arrayListOf(Title("견습 탐정",""), Title("묻고 더블로가!",""), Title( "숙련된 탐정", ""),Title("스핑크스",""), Title("바보",""))))
    }
    fun initLayout(){
        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        rView = v!!.findViewById(R.id.fw_rView)
        rView.layoutManager = layoutManager
        adapter = FollowAdapter(followList, context!!,false)
        rView.adapter = adapter
    }

}
