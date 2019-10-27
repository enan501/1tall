package safari.com.iltall.Data.Adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import safari.com.iltall.Data.Dataclass.Feed
import safari.com.iltall.R


class FeedAdapter(var items:ArrayList<Feed>, val context: Context): RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_feed,p0,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.title.text = items[p1].title
        p0.content.text = items[p1].content
        p0.img.setImageURI(Uri.parse("android.resource://safari.com.iltall/drawable/profile"))
    }
    interface OnItemClickListener{
        fun OnItemClick(holder:ViewHolder, data: Feed, position: Int)
    }
    interface OnItemLongClickListener {
        fun OnItemLongClick(holder: ViewHolder, view: View, data: Feed, position: Int)
    }
    var itemClickListener : OnItemClickListener? = null
    var itemLongClickListener : OnItemLongClickListener? = null

    inner class ViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var title: TextView
        var content: TextView
        init{
            img = itemView.findViewById(R.id.fd_img)
            title = itemView.findViewById(R.id.fd_title)
            content = itemView.findViewById(R.id.fd_content)
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