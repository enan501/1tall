package safari.com.iltall.Data.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import safari.com.iltall.Data.Dataclass.Quest
import safari.com.iltall.R


class QuestListAdapter(var items:ArrayList<Quest>, val context: Context): RecyclerView.Adapter<QuestListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_quest,p0,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.title.text =  items[p1].title
        p0.author.text = items[p1].author
        p0.solved.text =  items[p1].solved.toString()+"명이 풀었어요"
        p0.submitted.text = items[p1].submitted.toString()+"개의 문제가 더 있어요"
        p0.likes.text = "+"+ items[p1].likes

    }
    interface OnItemClickListener{
        fun OnItemClick(holder:ViewHolder, data: Quest, position: Int)
    }
    interface OnItemLongClickListener {
        fun OnItemLongClick(holder: ViewHolder, view: View, data: Quest, position: Int)
    }
    var itemClickListener : OnItemClickListener? = null
    var itemLongClickListener : OnItemLongClickListener? = null

    inner class ViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var title: TextView
        var author: TextView
        var solved: TextView
        var submitted: TextView
        var likes: TextView
        init{
            img = itemView.findViewById(R.id.qi_img)
            title = itemView.findViewById(R.id.qi_title)
            author = itemView.findViewById(R.id.qi_author)
            solved = itemView.findViewById(R.id.qi_solved)
            submitted = itemView.findViewById(R.id.qi_submitted)
            likes = itemView.findViewById(R.id.qi_likes)
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