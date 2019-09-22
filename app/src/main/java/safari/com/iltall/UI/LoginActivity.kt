package safari.com.iltall.UI

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.Utility
import safari.com.iltall.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {

    lateinit var kakaoCallback: SessionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //getHashKey()
        kakaoInit()
    }

    fun loginClick(view: View){

    }

    fun getHashKey(){
        val info = Utility.getPackageInfo(this, PackageManager.GET_SIGNATURES)
        if(info == null){
            Toast.makeText(this,"안됨", Toast.LENGTH_SHORT).show()
            Log.d("thisd","안됨")
        }

        for(signature in info!!.signatures){
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("HASH_KEY", Base64.encodeToString(md.digest(), Base64.NO_WRAP))
            }catch (e: NoSuchAlgorithmException){
                Log.w("해시키오류","Unable to get MessageDigest. signature =$signature",e)
            }
        }
    }

    fun kakaoInit(){
        kakaoCallback = SessionCallback()
        Session.getCurrentSession().addCallback(kakaoCallback)
        Log.d("KAKAOSESSION","SESSION")
        Session.getCurrentSession().checkAndImplicitOpen()
        Log.d("KAKAOSESSION","SESSION")
    }

    //카카오 콜백함수
    inner class SessionCallback : ISessionCallback {

        override fun onSessionOpenFailed(exception: KakaoException?) {
            Log.d("KAKAOSESSION","콜백실패 "+exception!!.errorType)
        }

        override fun onSessionOpened() {
            Log.d("KAKAOSESSION","SessionCallbackOpened")
            request()
        }
    }

    fun request(){
        var keys:ArrayList<String> = ArrayList()
        UserManagement.getInstance().me(keys,object:MeV2ResponseCallback(){
            override fun onSuccess(result: MeV2Response?) {
            //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Toast.makeText(applicationContext,result!!.id.toString(),Toast.LENGTH_SHORT).show()
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
             //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
