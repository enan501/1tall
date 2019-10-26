package safari.com.iltall.Data.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import safari.com.iltall.Data.Dataclass.QuestContent
import safari.com.iltall.R


class QuestContentAdapter(var items:ArrayList<QuestContent>, val context: Context): RecyclerView.Adapter<QuestContentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_quest_content,p0,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if(items[p1].isAddLast==1) {
            p0.content.visibility = View.GONE
            p0.add.visibility = View.VISIBLE
        }
        else if(items[p1].isAddLast == 2){
            var lp = p0.itemView.layoutParams
            lp.width = 280
            p0.itemView.layoutParams = lp
            p0.itemView.visibility = View.INVISIBLE
        }
        else if(items[p1].img != 0) {
            p0.img.visibility = View.VISIBLE
            p0.img.setImageResource(items[p1].img)
            //p0.content.height = 400
        }
    }
    interface OnItemClickListener{
        fun OnItemClick(holder:ViewHolder, data: QuestContent, position: Int)
    }
    interface OnItemLongClickListener {
        fun OnItemLongClick(holder: ViewHolder, view: View, data: QuestContent, position: Int)
    }
    var itemClickListener : OnItemClickListener? = null
    var itemLongClickListener : OnItemLongClickListener? = null

    inner class ViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var content: TextView
        var add: TextView
        init{
            img = itemView.findViewById(R.id.qc_img)
            content = itemView.findViewById(R.id.qc_content)
            add = itemView.findViewById(R.id.qc_add)
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