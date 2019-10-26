package safari.com.iltall.Data.Dataclass

data class Quest( var title:String, var author:String, var likes:Int, var solved:Int, var submitted:Int ,var isSolved:Boolean=false, var img:String="",var lat:Double=37.1253,var lon:Double=127.115)