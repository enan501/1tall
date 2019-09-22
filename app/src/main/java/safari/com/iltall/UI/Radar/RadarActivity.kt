package safari.com.iltall.UI.Radar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import safari.com.iltall.R

class RadarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar)

        var img = findViewById(R.id.splashimg) as ImageView
        var gifimg = GlideDrawableImageViewTarget(img)
        Glide.with(this).load(R.drawable.radar_sweep).into(gifimg)
    }
}
