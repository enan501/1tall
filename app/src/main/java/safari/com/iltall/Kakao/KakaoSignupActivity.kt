package safari.com.iltall.Kakao

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import safari.com.iltall.R
import safari.com.iltall.UI.LoginActivity

class KakaoSignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_signup)
        requestMe()
    }

    fun requestMe(){
        var keys:ArrayList<String> = ArrayList()
        UserManagement.getInstance().me(keys,object: MeV2ResponseCallback(){
            override fun onSuccess(result: MeV2Response?) {
                //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Toast.makeText(applicationContext,result!!.id.toString(), Toast.LENGTH_SHORT).show()
                redirectMainActivity()
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                redirectLoginActivity()
            }

        })
    }

    fun redirectLoginActivity(){
       var intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun redirectMainActivity(){

    }
}
