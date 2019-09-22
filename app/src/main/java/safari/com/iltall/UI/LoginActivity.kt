package safari.com.iltall.UI

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_login.*
import safari.com.iltall.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {

    lateinit var kakaoCallback: SessionCallback

    val CID = "482@naver.com"
    val CPASS = "1234"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //getHashKey()
        kakaoInit()
    }

    fun loginClick(view: View){
        val id = e_text.text.toString()
        val pass = p_text.text.toString()

        if(id == CID && pass == CPASS){
            //로그인 성공
        }
        else{
            //로그인 실패
        }
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
        Session.getCurrentSession().checkAndImplicitOpen()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)){
            return
        }

        super.onActivityResult(requestCode, resultCode, data)
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
                Log.v("LoginError",errorResult.toString())
            }

        })
    }
}
