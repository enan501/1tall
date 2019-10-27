package safari.com.iltall.Data.Dataclass

import java.io.Serializable

data class Follow(var img:String, var id:String, var solved:Int, var gave:Int,var title:String, var level:Int, var follower:Int, var following:Int, var rank:Int, var titleList:ArrayList<Title>) :Serializable