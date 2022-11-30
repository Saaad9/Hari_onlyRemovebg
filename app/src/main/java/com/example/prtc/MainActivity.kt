package com.example.prtc

import com.example.prtc.formatMessage
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.content.Intent
import android.net.Uri
import com.example.prtc.MainActivity2
import android.os.Bundle
import android.os.Process
import com.example.prtc.R
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.example.prtc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var Btn: Button? = null
    private var go_Gallery: Button? = null
    var tvText: TextView? = null

    //    //갤러리에서 이미지 선택한 후 startActivityForResult()함수로 재정의 된 onActivityResult 함수 호출
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val selectedImageUri = data!!.data
//        val intent = Intent(applicationContext, MainActivity2::class.java)
//
//        //이름이 image 인 이미지주소값을 string으로 송신 받을때 string으로 받고 Uri로 변환하여 사용
//        intent.putExtra("image", selectedImageUri.toString())
//        startActivity(intent)
//    }
//    /데이터 수신
//    val intent = intent
//
//    //받은 데이터를 Uri 형태로 변환
//    val uri = intent.extras!!.getString("image")
//    val Image = Uri.parse(uri)
//    showimage = findViewById<View>(R.id.showimage) as ImageView
    private lateinit var binding: ActivityMainBinding
    private val imageResult =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { uri ->
//                    binding.showimage.setImageURI(uri)
                val intent = Intent(this,MainActivity2::class.java)
                intent.putExtra("image",uri.toString())
//                    intent.extras!!.putString("image",uri.toString())
                startActivity(intent)

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //갤러리로 이동하여 이미지 선택
        go_Gallery = findViewById<View>(R.id.image_Sel) as Button
        go_Gallery!!.setOnClickListener {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            imageResult.launch("image/*")
        }


        Btn = findViewById<View>(R.id.exit_button) as Button
        // 종료 버튼
        Btn!!.setOnClickListener { Process.killProcess(Process.myPid()) }
    }
}