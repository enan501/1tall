package safari.com.iltall.UI.Radar


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.google.android.gms.maps.model.LatLng


import kotlinx.android.synthetic.main.activity_radar.*
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import safari.com.iltall.Data.Dataclass.*
import safari.com.iltall.R
import safari.com.iltall.UI.Network.NetworkActivity
import safari.com.iltall.UI.Quest.MakeQuestActivity
import safari.com.iltall.UI.Quest.QuestActivity
import safari.com.iltall.UI.Quest.QuestListActivity
import safari.com.iltall.UI.StatusActivity
import java.io.InputStream
import java.net.URI
import java.net.URL

class RadarActivity : AppCompatActivity() {

    val REQUEST_MAKE_QUEST = 1515
    val LOCATION_REQUEST = 4444
    lateinit var mapView:MapView
    lateinit var user: Follow
    var putMarker:ArrayList<Quest> = arrayListOf()
    val markerPoint:ArrayList<Quest> = arrayListOf(
        Quest("건국대하ㄱ교정복",User("개미뇸", "android.resource://safari.com.iltall/drawable/profile"), 10,5,10,false,"null",MyLocation(37.543700,127.077371),
        arrayListOf(QuestContent("개미는 뭘까","",0),QuestContent("개미는 오늘도","",0)),"힌트업다","개미핥기"),
        Quest("으아악",User("악","android.resource://safari.com.iltall/drawable/profile"),12,2,3,false,"null",MyLocation(37.541990,127.073852),
        arrayListOf(QuestContent("으악ㅇ극각극가각각아앙아ㅏㄱ","",0)),"힌ㅌ아아악ㄱ","답"))
    lateinit var CQ:CustomQuiz

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar)
        /*
        var uri = Uri.parse("android.resource://safari.com.iltall/drawable/profile");
        var stream = getContentResolver().openInputStream(uri);
        Log.d("URI",stream!!.toString())

         */
       // btnInVIsible()
        initPermission()
        initMap()
        initListener()
        initData()
    }

    private fun initData() {
        user = Follow("","whale",100,30,"견습 탐정",15,130,127, 1, arrayListOf(Title("견습 탐정","android.resource://safari.com.iltall/drawable/bronze"), Title("묻고 더블로가!","android.resource://safari.com.iltall/drawable/gold"), Title( "숙련된 탐정", "android.resource://safari.com.iltall/drawable/silver"), Title("바보","android.resource://safari.com.iltall/drawable/gold")))
    }

    private fun initListener() {
        rd_network.setOnClickListener {
            val nextIntent = Intent(this, NetworkActivity::class.java)
            startActivity(nextIntent)
        }
        rd_make_quest.setOnClickListener {
            val nextIntent = Intent(this, MakeQuestActivity::class.java)
            startActivityForResult(nextIntent,REQUEST_MAKE_QUEST)
        }
        rd_solve_quest.setOnClickListener {
            //val nextIntent = Intent(this, QuestActivity::class.java)
            val nextIntent = Intent(this, QuestListActivity::class.java)
            nextIntent.putExtra("QUIZ",putMarker)
            startActivity(nextIntent)
        }
        rd_status.setOnClickListener {
            val nextIntent = Intent(this, StatusActivity::class.java)
            nextIntent.putExtra("user",user)
            startActivity(nextIntent)
        }
    }

    fun initMap(){
        mapView = MapView(this)

        CQ = CustomQuiz(this,getCurLoc()!!)
        CQ.setData(markerPoint)
        mapView.setCalloutBalloonAdapter(CQ)

        drawMyLoc()
        makeMarker()
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
        mapView.setCurrentLocationEventListener(object :MapView.CurrentLocationEventListener{
            override fun onCurrentLocationUpdateFailed(p0: MapView?) {
            }

            override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {
                drawMyLoc()
            }

            override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
            }

            override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
            }
        })
        map_view.addView(mapView)
    }

    fun getCurLoc(): MyLocation? {
        if(checkAppPermission(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION))) {

            val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            val providers = lm!!.getProviders(true)
            var location: android.location.Location? = null
            for (provider in providers) {
                val l = lm!!.getLastKnownLocation(provider) ?: continue
                if (location == null || l.getAccuracy() < location!!.getAccuracy()) {
                    // Found best last known location: %s", l);
                    location = l
                }
            }
            return MyLocation(location!!.latitude,location!!.longitude)
        }
        else{
            initPermission()
        }
        return null
    }

    fun drawMyLoc(){
        var location = getCurLoc()
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location!!.latitude,location!!.longitude),true)

        /*
        var bitmap = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.working)
        var img = Bitmap.createScaledBitmap(bitmap,100,100,true)
        var canvas = Canvas()
        canvas.drawBitmap(img,(location!!.latitude-50).toFloat(),(location!!.longitude-50).toFloat(),null)
        add_rader.setImageDrawable(BitmapDrawable(resources,bitmap))*/
        /*
        var gifimg = GlideDrawableImageViewTarget(add_rader)
        Glide.with(this).load(R.raw.working).override(50,50).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(gifimg)*/
    }



    fun makeMarker(){
        for (point in markerPoint){
            var marker = MapPOIItem()
            marker.itemName = point.title
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(point.location.latitude,point.location.longitude)
            marker.markerType = MapPOIItem.MarkerType.CustomImage
            marker.customImageResourceId = R.drawable.marker
            marker.isCustomImageAutoscale = false
            marker.setCustomImageAnchor(0.5f,1.0f)
            mapView.addPOIItem(marker)
        }
    }


    fun btnVisible(q:Quest){

      //  hideLayer.visibility = VISIBLE
        quiz_put.setOnClickListener {
            putMarker.add(q!!)
        }
        quiz_solve.setOnClickListener {
            val nextIntent = Intent(this, QuestActivity::class.java)
           nextIntent.putExtra("PLAY",q!!)
            startActivity(nextIntent)
           // btnInVIsible()
        }
    }

    fun btnInVIsible(){

    }

    fun checkAppPermission(requestPermission:Array<String>):Boolean{
        val requestResult = BooleanArray(requestPermission.size)
        for (i in requestResult.indices){
            requestResult[i] = ContextCompat.checkSelfPermission(this,requestPermission[i])==PackageManager.PERMISSION_GRANTED
            if(!requestResult[i]){
                return false
            }
        }
        return true
    }

    fun askPermission(requestPermission:Array<String>,REQ_PERMISSION:Int){
        ActivityCompat.requestPermissions(this,requestPermission,REQ_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_REQUEST->{
                if(checkAppPermission(permissions)){
                  //  Toast.makeText(applicationContext,"위치 권한을 허락합니다",Toast.LENGTH_SHORT).show()
                }else{
                  //  Toast.makeText(applicationContext,"위치 권한이 취소되었습니다",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    fun initPermission(){
        if(checkAppPermission(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION))){
            Toast.makeText(this,"현재 위치를 사용합니다",Toast.LENGTH_SHORT).show()
        }else{
            askPermission(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_REQUEST)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_MAKE_QUEST && resultCode == Activity.RESULT_OK){
            var title = data?.getStringExtra("title")
            var content = data?.getSerializableExtra("content") as ArrayList<QuestContent>
            var hint = data?.getStringExtra("hint")
            var answer = data?.getStringExtra("answer")
            //TODO("이곳에 마커 추가를 만들어주세요")
            var q = Quest(title!!,User("익명","android.resource://safari.com.iltall/drawable/profile"),0,0,0,false,"null",getCurLoc()!!,content,hint,answer)
            markerPoint.add(q)
            makeMarker()
            CQ.setData(markerPoint)
            Toast.makeText(this,"문제가 등록되었습니다!",Toast.LENGTH_SHORT).show()
        }
    }
}
