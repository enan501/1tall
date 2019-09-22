package safari.com.iltall.UI

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_join.*
import safari.com.iltall.Data.SHA256.Security
import safari.com.iltall.R
import safari.com.iltall.UI.Radar.RadarActivity

class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
    }

    fun Join(view: View){
        val id = je_text.text.toString()
        var pass = jp_text.text.toString()
        val name = jn_text.text.toString()

        var s = Security()
        pass = s.digest("SHA-256",pass)


        //디비에 정보 저장해주면 됨

        var intent = Intent(applicationContext, RadarActivity::class.java)
        startActivity(intent)
        finish()
    }
}
