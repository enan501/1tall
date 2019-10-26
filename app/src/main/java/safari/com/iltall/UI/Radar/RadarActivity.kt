package safari.com.iltall.UI.Radar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.google.android.gms.maps.model.LatLng


import kotlinx.android.synthetic.main.activity_radar.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import safari.com.iltall.R
import safari.com.iltall.UI.Network.NetworkActivity
import safari.com.iltall.UI.Quest.MakeQuestActivity
import safari.com.iltall.UI.Quest.QuestListActivity
import safari.com.iltall.UI.StatusActivity

class RadarActivity : AppCompatActivity() {

    val LOCATION_REQUEST = 4444
    lateinit var mapView:MapView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar)
        initMap()
        initListener()
    }

    private fun initListener() {
        rd_network.setOnClickListener {
            val nextIntent = Intent(this, NetworkActivity::class.java)
            startActivity(nextIntent)
        }
        rd_make_quest.setOnClickListener {
            val nextIntent = Intent(this, MakeQuestActivity::class.java)
            startActivity(nextIntent)
        }
        rd_solve_quest.setOnClickListener {
            //val nextIntent = Intent(this, QuestActivity::class.java)
            val nextIntent = Intent(this, QuestListActivity::class.java)
            startActivity(nextIntent)
        }
        rd_status.setOnClickListener {
            val nextIntent = Intent(this, StatusActivity::class.java)
            startActivity(nextIntent)
        }
    }

    @SuppressLint("MissingPermission")
    fun initMap(){
        mapView = MapView(this)
        initPermission()
        getCurLoc()
        mapView.zoomIn(false)
        mapView.zoomOut(false)

        map_view.addView(mapView)
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
        mapView.setCurrentLocationEventListener(object :MapView.CurrentLocationEventListener{
            override fun onCurrentLocationUpdateFailed(p0: MapView?) {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                getCurLoc()
            }

            override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        map_view.addView(mapView)
       mapView.setCalloutBalloonAdapter(CustomQuiz(this))
        getCurLoc()
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
        mapView.setPOIItemEventListener(object:MapView.POIItemEventListener{
            override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
                //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Toast.makeText(applicationContext,p1!!.itemName,Toast.LENGTH_SHORT).show()
            }

            override fun onCalloutBalloonOfPOIItemTouched(
                p0: MapView?,
                p1: MapPOIItem?,
                p2: MapPOIItem.CalloutBalloonButtonType?
            ) {
                //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Toast.makeText(applicationContext,p1!!.itemName,Toast.LENGTH_SHORT).show()
            }

            override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
                //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
                //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Toast.makeText(applicationContext,p1!!.itemName,Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun getCurLoc(){
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
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location!!.latitude,location!!.longitude),true)
            var marker = MapPOIItem() as MapPOIItem
            marker.itemName = "나!"
            marker.tag = 0
            marker.markerType = MapPOIItem.MarkerType.YellowPin
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(location!!.latitude,location!!.longitude)
            mapView.addPOIItem(marker)
        }
        else{
            initPermission()
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

}
