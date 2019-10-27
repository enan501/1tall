package safari.com.iltall.UI.Radar


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import kotlinx.android.synthetic.main.activity_radar.*
import kotlinx.android.synthetic.main.quiz_ballon.view.*
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

class RadarActivity : AppCompatActivity() {

    var btnList:ArrayList<Button> = arrayListOf()
    val REQUEST_MAKE_QUEST = 1515
    val LOCATION_REQUEST = 4444
    val CORRECT = 9999
    lateinit var mapView:MapView
    lateinit var user: Follow
    var nowMarker:Quest?= null
    lateinit var curLocation: MyLocation
    var putQ:ArrayList<Quest> = arrayListOf()
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
        var gifimg = GlideDrawableImageViewTarget(add_rader)
        Glide.with(this).load(R.raw.working).override(50,50).into(gifimg)
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
            nextIntent.putExtra("QUIZ",putQ)
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
                addMarkers()
            }

            override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
            }

            override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
            }
        })



       // map_view.addView(mapView)


    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        addMarkers()
    }
    private fun addMarkers() {
        curLocation = getCurLoc()!!
        for(rbtn in btnList)
            map_view.removeView(rbtn)
        btnList.clear()
        for(marker in markerPoint){
            if(dist(curLocation, marker.location)<100){
                var wc = par.width/2-40
                var hc = par.height/2-40
                var btn = Button(this)
                var y = 111*(marker.location.latitude - curLocation.latitude)
                var x = (Math.cos(marker.location.latitude)*6400*2*3.14/360)*(marker.location.longitude - curLocation.longitude)
                var layoutParams = FrameLayout.LayoutParams(80,80)
                btn.setBackgroundResource(R.drawable.marker)
                Log.v("ss",curLocation.latitude.toString()+" / "+curLocation.longitude)
                Log.v("ss",marker.location.latitude.toString()+" / "+marker.location.longitude)
                Log.v("ss",(wc + 1000 * x).toInt().toString()+" / "+(hc + 700 * y).toInt())
                layoutParams.leftMargin = (wc + 1500 * x).toInt()
                layoutParams.topMargin = (hc - 1900 * y).toInt()
                btn.layoutParams = layoutParams
                btn.setOnClickListener {

                    val builder = AlertDialog.Builder(this) //alert 다이얼로그 builder 이용해서 다이얼로그 생성
                    val questDialog = layoutInflater.inflate(R.layout.quiz_ballon, null)
                    questDialog.quiz_title.text = marker.title
                    questDialog.user_name.text = marker.user.name
                    questDialog.saveBtn.setOnClickListener {
                        //담기
                        var check = true
                        for (i in 0..putQ.size-1){
                            if(putQ[i].title == marker.title){
                                Toast.makeText(this,"이미 담긴 문제입니다",Toast.LENGTH_SHORT).show()
                                check = false
                            }
                        }
                        if(check){
                            Toast.makeText(this,"문제를 담았습니다",Toast.LENGTH_SHORT).show()
                            putQ.add(marker)
                        }
                    }
                    questDialog.playBtn.setOnClickListener {
                        if(!marker.isSolved){
                            nowMarker = marker
                            Toast.makeText(this,nowMarker!!.title,Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,QuestActivity::class.java)
                            intent.putExtra("PLAY",marker)
                            startActivityForResult(intent,CORRECT)
                        }else{
                            Toast.makeText(this,"이미 푼 문제입니다",Toast.LENGTH_SHORT).show()
                        }
                        //풀기
                    }
                    questDialog.user_image.setImageURI(Uri.parse(marker.user.image))
                    builder.setView(questDialog)
                    val dialog = builder.create()
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//                    val wmlp = dialog.window!!.attributes
//
//                    wmlp.gravity = Gravity.TOP or Gravity.CENTER
//                    wmlp.y = 200   //y position
                    dialog.show()
                }
                map_view.addView(btn)
                btnList.add(btn)

            }
        }
    }
    fun dist(cur: MyLocation, marker:MyLocation):Double{
        var x = (Math.cos(marker!!.longitude)*6400*2*3.14/360)*Math.abs(marker!!.longitude-cur!!.longitude)
        var y = 111*Math.abs(marker!!.latitude-cur.latitude)
        return Math.sqrt(x*x+y*y)
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
        curLocation = getCurLoc()!!
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(curLocation!!.latitude,curLocation!!.longitude),true)

        /*
        var bitmap = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.working)
        var img = Bitmap.createScaledBitmap(bitmap,100,100,true)
        var canvas = Canvas()
        canvas.drawBitmap(img,(location!!.latitude-50).toFloat(),(location!!.longitude-50).toFloat(),null)
        add_rader.setImageDrawable(BitmapDrawable(resources,bitmap))*/


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
        }else if(requestCode==CORRECT && resultCode == Activity.RESULT_OK){
            for (i in 0..markerPoint.size-1){
                if(markerPoint[i].title == nowMarker!!.title){
                    markerPoint[i].isSolved = true
                    break
                  //  markerPoint.remove(nowMarker!!)
                }
            }
        }
    }
}
