package safari.com.iltall.Data.Adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import safari.com.iltall.Data.Dataclass.Follow
import safari.com.iltall.R


class FollowAdapter(var items:ArrayList<Follow>, val context: Context, var isRank:Boolean): RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_follow,p0,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if(isRank){
            p0.rank.visibility = View.VISIBLE
            p0.rank.text = items[p1].rank.toString() + "위"
        }
        p0.img.setImageURI(Uri.parse(items[p1].img))
        p0.id.text = items[p1].id
        p0.solved.text =  items[p1].solved.toString()+"개의 문제를 풀었습니다"
        p0.submitted.text = ""

    }
    interface OnItemClickListener{
        fun OnItemClick(holder:ViewHolder, data: Follow, position: Int)
    }
    interface OnItemLongClickListener {
        fun OnItemLongClick(holder: ViewHolder, view: View, data: Follow, position: Int)
    }
    var itemClickListener : OnItemClickListener? = null
    var itemLongClickListener : OnItemLongClickListener? = null

    inner class ViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var id: TextView
        var solved: TextView
        var submitted: TextView
        var rank: TextView
        init{
            img = itemView.findViewById(R.id.fw_img)
            rank = itemView.findViewById(R.id.rk_rank)
            id = itemView.findViewById(R.id.fw_id)
            solved = itemView.findViewById(R.id.fw_solved)
            submitted = itemView.findViewById(R.id.fw_submitted)
            itemView.setOnClickListener{
                val position = adapterPosition
                itemClickListener?.OnItemClick(this,items[position],position)
            }
            itemView.setOnLongClickListener{
                val position = adapterPosition
                itemLongClickListener?.OnItemLongClick(this,it, items[position],position)
                true
            }
        }

    }
}