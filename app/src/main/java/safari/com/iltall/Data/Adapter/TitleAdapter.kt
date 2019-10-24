package safari.com.iltall.Data.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import safari.com.iltall.Data.Dataclass.Title
import safari.com.iltall.R

    class TitleAdapter(var items:ArrayList<Title>, val context: Context): RecyclerView.Adapter<TitleAdapter.ViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            val v = LayoutInflater.from(p0.context).inflate(R.layout.item_title,p0,false)
            return ViewHolder(v)
        }
        override fun getItemCount(): Int {
            return items.size
        }
        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            p0.img.setImageResource(R.drawable.trophy)
            p0.Title.text = items[p1].name

        }
        interface OnItemClickListener{
            fun OnItemClick(holder:ViewHolder, data: Title, position: Int)
        }
        interface OnItemLongClickListener {
            fun OnItemLongClick(holder: ViewHolder, view: View, data: Title, position: Int)
        }
        var itemClickListener : OnItemClickListener? = null
        var itemLongClickListener : OnItemLongClickListener? = null

        inner class ViewHolder(itemView: View)
            :RecyclerView.ViewHolder(itemView){
            var img: ImageView
            var Title: TextView
            init{
                img = itemView.findViewById(R.id.title_img)
                Title = itemView.findViewById(R.id.title_name)
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