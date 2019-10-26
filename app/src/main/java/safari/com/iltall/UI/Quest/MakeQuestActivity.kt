package safari.com.iltall.UI.Quest

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.LinearSnapHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_make_quest.*
import safari.com.iltall.Data.Adapter.CenterZoomLayoutManager
import safari.com.iltall.Data.Adapter.QuestContentAdapter
import safari.com.iltall.Data.Dataclass.QuestContent
import java.io.File




class MakeQuestActivity : AppCompatActivity() {
    var snapHelper = LinearSnapHelper()
    val EXTERNAL_REQUEST = 123
    val CAMERA_REQUEST = 321
    val SELECT_IMAGE = 9999
    val CAMERA_IMAGE = 1111
    var img_url: String = "" // 이미지 URI
    lateinit var mImageCaptureUri:Uri
    lateinit var mPhoto:String
    var curPos: Int = 0
    lateinit var file: File
    lateinit var smoothScroller:LinearSmoothScroller
    lateinit var layoutManager:CenterZoomLayoutManager
    lateinit var contentList:ArrayList<QuestContent>
    lateinit var adapter: QuestContentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(safari.com.iltall.R.layout.activity_make_quest)
        init()
    }
    fun init(){
        initData()
        initLayout()
        initListener()
        initCameraPermission()
        initAlbumPermission()
    }


    private fun initData() {
        contentList = arrayListOf()
        contentList.add(QuestContent("","",2))
        contentList.add(QuestContent("","",2))
        contentList.add(QuestContent("","",0))
        contentList.add(QuestContent("","",1))
        contentList.add(QuestContent("","",2))
    }
    private fun initLayout() {
        layoutManager = CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        smoothScroller = object : LinearSmoothScroller(this) {
            override fun getHorizontalSnapPreference(): Int {
                return SNAP_TO_END
            }
        }
        mq_rView.layoutManager = layoutManager
        snapHelper.attachToRecyclerView(mq_rView) //아이템 가운데로 끌어 맞추기
        adapter = QuestContentAdapter(contentList)
        mq_rView.adapter = adapter
        smoothScroller.targetPosition = 1
        smoothScroller.computeScrollVectorForPosition(1)
        contentList.removeAt(0)
        layoutManager.startSmoothScroll(smoothScroller)
        adapter.notifyDataSetChanged()
    }
    private fun createImageFile():File{
        val url = "tmp_" + System.currentTimeMillis().toString()

        var storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        var image = File.createTempFile(url,".jpg",storageDir)
        mPhoto = image.absolutePath
        return image
    }


    private fun initListener() {
        adapter.itemClickListener = object : QuestContentAdapter.OnItemClickListener {
            override fun OnCameraClick(
                holder: QuestContentAdapter.ViewHolder,
                data: QuestContent,
                position: Int
            ) {
                val cintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                file = createImageFile()
                mImageCaptureUri =  FileProvider.getUriForFile(this@MakeQuestActivity, "safari.com.iltall.provider", file);
                cintent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri)
                curPos = position
                startActivityForResult(cintent, CAMERA_IMAGE)
            }

            override fun OnAlbumClick(
                holder: QuestContentAdapter.ViewHolder,
                data: QuestContent,
                position: Int
            ) {
                initAlbumPermission()
                curPos = position
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                startActivityForResult(intent, SELECT_IMAGE)
            }

            override fun OnItemClick(holder: QuestContentAdapter.ViewHolder, data: QuestContent, position: Int) {

                if(data.isAddLast==1){ //추가
                    contentList.add(position,QuestContent("","",0))
                }
                adapter.notifyDataSetChanged()
                mq_rView.adapter = adapter
                layoutManager.scrollToPosition(position)
                smoothScroller.targetPosition = position
                smoothScroller.computeScrollVectorForPosition(position)
                layoutManager.startSmoothScroll(smoothScroller)
            }

        }
        mq_hint_btn.setOnClickListener {
            mq_hint_btn.visibility = View.GONE
            mq_hint_block.visibility = View.VISIBLE
        }
        mq_submit.setOnClickListener {
            var intent = Intent()
            intent.putExtra("title",mq_title.text)
            //intent.putExtra("text",mq_text.text)
            intent.putExtra("hint",mq_hint.text)
            intent.putExtra("answer",mq_answer.text)
            setResult(RESULT_OK,intent)
            finish()
        }
    }
    fun getPathFromUri(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToNext()
        val path = cursor.getString(cursor.getColumnIndex("_data"))
        cursor.close()
        return path
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == SELECT_IMAGE) {
                if (resultCode == Activity.RESULT_OK) {
                    contentList[curPos].img = getPathFromUri(data!!.data)
                    adapter.notifyDataSetChanged()

                }
            }
            if (requestCode == CAMERA_IMAGE) {
                if (resultCode == Activity.RESULT_OK) {

                    contentList[curPos].img = mImageCaptureUri.path
                    adapter.notifyDataSetChanged()

                }
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

    fun initAlbumPermission(){
        if(checkAppPermission(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE))){
        }else{
            askPermission(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),EXTERNAL_REQUEST)
        }
    }
    fun initCameraPermission(){
        if(checkAppPermission(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))){
        }else{
            askPermission(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),CAMERA_REQUEST)
        }
    }
}
