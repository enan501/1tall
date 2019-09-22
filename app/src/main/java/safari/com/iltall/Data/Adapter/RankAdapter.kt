package safari.com.iltall.Data.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import safari.com.iltall.Data.Dataclass.Rank
import safari.com.iltall.R


class RankAdapter(var items:ArrayList<Rank>, val context: Context): RecyclerView.Adapter<RankAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_rank,p0,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.id.text = items[p1].id
        p0.rank.text = (p1+1).toString() + "위"
        p0.solved.text = "푼 문제 : " + items[p1].solved.toString()
        p0.submitted.text = "낸 문제 : " + items[p1].submitted.toString()

    }
    interface OnItemClickListener{
        fun OnItemClick(holder:ViewHolder, data: Rank, position: Int)
    }
    interface OnItemLongClickListener {
        fun OnItemLongClick(holder: ViewHolder, view: View, data: Rank, position: Int)
    }
    var itemClickListener : OnItemClickListener? = null
    var itemLongClickListener : OnItemLongClickListener? = null

    inner class ViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var rank: TextView
        var id: TextView
        var solved: TextView
        var submitted: TextView
        init{
            img = itemView.findViewById(R.id.rk_img)
            rank = itemView.findViewById(R.id.rk_rank)
            id = itemView.findViewById(R.id.rk_id)
            solved = itemView.findViewById(R.id.rk_solved)
            submitted = itemView.findViewById(R.id.rk_submitted)
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