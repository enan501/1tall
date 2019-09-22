package safari.com.iltall.Data.SHA256

import android.os.Message
import android.util.Log
import java.io.File
import java.security.MessageDigest

class Security {

   fun digest( engine:String,pass:String ):String{
       val md = MessageDigest.getInstance(engine)

       val byte = ByteArray(4096)

       md.update(byte,0,pass.length)

       val byteData = md.digest()

       return byteData.toString()
   }
}