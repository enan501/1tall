package safari.com.iltall.Data.Dataclass

import android.os.Parcelable
import java.io.Serializable

data class Quest( var title:String, var author:String, var likes:Int, var solved:Int, var submitted:Int ,var isSolved:Boolean=false, var img:String="",var location: MyLocation,var text:String, var hint:String,var answer:String) :Serializable