package com.example.prtc

import androidx.appcompat.app.AppCompatActivity
import android.os.Environment
import android.os.Bundle
import com.example.prtc.R
import android.content.Intent
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import android.annotation.SuppressLint
import android.media.Image
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import com.example.prtc.databinding.ActivityMain2Binding
import com.slowmac.autobackgroundremover.OnBackgroundChangeListener
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    var showimage: ImageView? = null
    private var Save_btn: Button? = null
    private var RemoveBgBtn: ImageView? = null

    private lateinit var binding: ActivityMain2Binding
    private val imageResult =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { uri ->
                binding.showimage.setImageURI(uri)
            }
        }

    //배경삭제 함수
    private fun removeBg() {
        binding.showimage.invalidate()
        BackgroundRemover.bitmapForProcessing(
            binding.showimage.drawable.toBitmap(),
            true,
            object : OnBackgroundChangeListener {
                override fun onSuccess(bitmap: Bitmap) {
                    binding.showimage.setImageBitmap(bitmap)
                }

                override fun onFailed(exception: Exception) {
                    Toast.makeText(this@MainActivity2, "Error Occur", Toast.LENGTH_SHORT).show()
                }

            })
    }

    // 선택한 이미지 내부 저장소에 저장
    private fun saveBitmapToJpeg(bitmap: Bitmap) {
        val imgName = "saved$time.jpg"
        val tempFile = File(outputFilePath, imgName) // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile() // 자동으로 빈 파일을 생성하기
            val out = FileOutputStream(tempFile) // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) // compress 함수를 사용해 스트림에 비트맵을 저장하기
            val saveFile = File(outputFilePath)

            // 파일을 단순 저장하면 로드가 안됨 이미지 스캐닝 위해서 db에 업데이트한다. 이미지 갤러리 로드 위한 코드
            this.applicationContext.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(saveFile)
                )
            )
            out.close() // 스트림 닫아주기
            Toast.makeText(applicationContext, "파일 저장 성공", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "파일 저장 실패", Toast.LENGTH_SHORT).show()
        }
    }

    //현재 시간 구하는 함수
    private fun get_now_time(): String {
        val now = System.currentTimeMillis() // 현재 시간을 가져온다.
        val mDate = Date(now) // Date형식으로 고친다.
        // 날짜, 시간을 가져오고 싶은 형태로 가져올 수 있다.
        @SuppressLint("SimpleDateFormat") val simpleDate =
            SimpleDateFormat("yyyy-MM-dd_hh_mm_ss", Locale.KOREA)
        return simpleDate.format(mDate)
    }

    var time = get_now_time()
    private val outputFilePath = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES
    ).toString()



    //main 문
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        //데이터 수신
        val intent = intent

        //받은 데이터를 Uri 형태로 변환
        val uri = intent.extras!!.getString("image")
        val Image = Uri.parse(uri)
        showimage = findViewById<View>(R.id.showimage) as ImageView

        // ImageView 영역에 이미지를 설정한다. MainActivity 에서 고른 사진을 설정하는 것임..
        showimage!!.setImageURI(Image)


        //Save_btn 기능 적용
        Save_btn = findViewById<View>(R.id.save) as Button
        Save_btn!!.setOnClickListener {
            val resolver = contentResolver
            try {
                val instream = resolver.openInputStream(Image)
                val imgBitmap = BitmapFactory.decodeStream(instream)
                instream!!.close() // 스트림 닫아주기
                saveBitmapToJpeg(imgBitmap) // 내부 저장소에 저장
                //                    Toast.makeText(getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "파일 불러오기 실패", Toast.LENGTH_SHORT).show()
            }
        }

        //배경삭제 버튼
        RemoveBgBtn = findViewById<ImageView>(R.id.removeBgBtn)
        RemoveBgBtn!!.setOnClickListener{
            removeBg()
        }
    }


}