package safari.com.iltall.Data.Adapter

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import safari.com.iltall.Data.Dataclass.QuestContent




class QuestContentAdapter(var items:ArrayList<QuestContent>): RecyclerView.Adapter<QuestContentAdapter.ViewHolder>() {
    fun update(){

    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(safari.com.iltall.R.layout.item_quest_content,p0,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if(items[p1].isAddLast==0) {
            p0.itemView.visibility = View.VISIBLE
            if(items[p1].img.isNotEmpty()) {
                p0.img.visibility = View.VISIBLE
                p0.img.scaleType = ImageView.ScaleType.FIT_CENTER
                p0.img.setImageBitmap(BitmapFactory.decodeFile(items[p1].img))
                //p0.content.height = 400
            }
            p0.text.setText(items[p1].text)
            p0.text.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    items[p1].text = p0.text.text.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }
        else if(items[p1].isAddLast==1) {
            p0.text.visibility = View.GONE
            p0.album.visibility = View.GONE
            p0.camera.visibility = View.GONE
            p0.add.visibility = View.VISIBLE
        }
        else if(items[p1].isAddLast == 2){
            var lp = p0.itemView.layoutParams
            lp.width = 280
            p0.itemView.layoutParams = lp
            p0.itemView.visibility = View.INVISIBLE
        }
    }
    interface OnItemClickListener{
        fun OnItemClick(holder:ViewHolder, data: QuestContent, position: Int)
        fun OnCameraClick(holder:ViewHolder, data: QuestContent, position: Int)
        fun OnAlbumClick(holder:ViewHolder, data: QuestContent, position: Int)
    }
    interface OnItemLongClickListener {
        fun OnItemLongClick(holder: ViewHolder, view: View, data: QuestContent, position: Int)
    }
    var itemClickListener : OnItemClickListener? = null
    var itemLongClickListener : OnItemLongClickListener? = null

    inner class ViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var text: EditText
        var add: TextView
        var camera: ImageButton
        var album: ImageButton
        init{
            img = itemView.findViewById(safari.com.iltall.R.id.qc_img)
            text = itemView.findViewById(safari.com.iltall.R.id.qc_content)
            add = itemView.findViewById(safari.com.iltall.R.id.qc_add)
            camera = itemView.findViewById(safari.com.iltall.R.id.qc_camera)
            album = itemView.findViewById(safari.com.iltall.R.id.qc_album)
            itemView.setOnClickListener{
                val position = adapterPosition
                if(position != 0 && position != items.size-1)
                    itemClickListener?.OnItemClick(this,items[position],position)
            }
            itemView.setOnLongClickListener{
                val position = adapterPosition
                itemLongClickListener?.OnItemLongClick(this,it, items[position],position)
                true
            }
            camera.setOnClickListener {
                val position = adapterPosition
                itemClickListener?.OnCameraClick(this,items[position],position)
            }
            album.setOnClickListener {
                val position = adapterPosition
                itemClickListener?.OnAlbumClick(this,items[position],position)
            }

        }
    }
}