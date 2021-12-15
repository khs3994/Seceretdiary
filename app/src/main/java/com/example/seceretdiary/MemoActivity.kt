package com.example.seceretdiary

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.example.seceretdiary.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {

    private val TAG = "DiaryActivity"
    private lateinit var binding: ActivityMemoBinding
    private val handler = Handler(Looper.getMainLooper())//Handler: 메인스레드와 생성된 스레드를 연결해주는 기능

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDetailEditText()

    }

    private fun initDetailEditText() {
        val detail = getSharedPreferences("diary", Context.MODE_PRIVATE)
        binding.diaryEdtx.setText(detail.getString("detail", ""))



        val runnable = Runnable { //Thread기능 이용
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", binding.diaryEdtx.text.toString())
            }

            Log.d( TAG,"SAVE!!!! ${binding.diaryEdtx.text.toString()}")
        }

        binding.diaryEdtx.addTextChangedListener {
            Log.d( TAG,"TextChanged :: $it")
            handler.removeCallbacks(runnable)//이전에 실행된 콜백이 있다면 삭제 없으면 그대로 놔둠
            handler.postDelayed(runnable, 500) //딜레이 시간마다 한번씩 저장
        }
    }

}
