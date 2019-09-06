package safari.com.iltall.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.auth.ISessionCallback
import com.kakao.util.exception.KakaoException
import safari.com.iltall.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    //카카오 콜백함수
    inner class SessionCallback : ISessionCallback {

        override fun onSessionOpenFailed(exception: KakaoException?) {
            Log.d("KAKAOSESSION","콜백실패 "+exception!!.errorType)
        }

        override fun onSessionOpened() {
            Log.d("KAKAOSESSION","SessionCallbackOpened")
        }
    }
}
