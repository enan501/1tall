package safari.com.iltall.UI.Quest


import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_quest.view.*
import safari.com.iltall.Data.Dataclass.QuestContent
import safari.com.iltall.R

/**
 * A simple [Fragment] subclass.
 */
class QuestFragment : Fragment() {

    lateinit var content:QuestContent
    lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_quest, container, false)
        content = arguments!!.getSerializable("content") as QuestContent
        init()
        return v
    }
    fun init(){
        if(content.img.isNotEmpty()){
            v.qf_img.visibility = View.VISIBLE
            v.qf_img.setImageBitmap(BitmapFactory.decodeFile(content.img))
        }
        v.qf_text.text = content.text
    }


}
