package com.example.seceretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.example.seceretdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val numberPicker1: NumberPicker by lazy {
        binding.numberpicker1
            .apply { //1번 Numberpicker의 범위 설정
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker2: NumberPicker by lazy {
        binding.numberpicker2
            .apply { //2번 Numberpicker의 범위 설정
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3: NumberPicker by lazy {
        binding.numberpicker3
            .apply { //3번 Numberpicker의 범위 설정
                minValue = 0
                maxValue = 9
            }
    }

    private var changePasswordMode = false //비밀번호 변경 모드의 boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        numberPicker1//
        numberPicker2//---범위를 지정하려면 한번씩은 호출해야하기 때문에 앞에서 호출
        numberPicker3//

        binding.OpenButton.setOnClickListener {
            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show() //비번을 변경하려할시 changepwbtn의 onclick리스너에서 비번이 맞는지 검사후
                //true를 반환해주면 이 구문이 실행
                return@setOnClickListener
            }

            val passwordPreferences =
                getSharedPreferences("password", Context.MODE_PRIVATE)//파일을 만들어서 데이터를 로컬에 저장
            val passwordFromuser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"//numberpicker의 값을 String형으로 일자로 붙임
            if (passwordPreferences.getString("password", "000").equals(passwordFromuser)) {
                                                                    // 사용자가 비번을 지정하지 않았을땐 기본 값으로 000제공
                val intent = Intent(this, MemoActivity::class.java)
                startActivity(intent) // Memo작성 액티비티로 이동
            } else {
                dialogerror()
            }
        }

        binding.changepwbtn.setOnClickListener {

            val passwordPreferences =
                getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromuser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (changePasswordMode) {
                passwordPreferences.edit(true) { //passwordpreferences의값을 사용자가 지정한 값으로 변경
                    putString("password", passwordFromuser)
                }

                changePasswordMode = false
                binding.changepwbtn.setBackgroundColor(Color.BLACK)


            } else {
                // changePasswordMode 가 활성화됨 :: 비밀번호가 맞는지를 체크
                if (passwordPreferences.getString("password", "000").equals(passwordFromuser)) {//원래 비밀번호를 입력해야 비밀번호 변경 가능
                    changePasswordMode = true//여기서 true로 지정하면 54번 줄에있는 Toast메시지 출력
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
                    binding.changepwbtn.setBackgroundColor(Color.RED)

                } else {
                    dialogerror()
                }

            }

        }
    }

    private fun dialogerror() { //에러 다이얼로그 출력 함수
        AlertDialog.Builder(this)
            .setTitle("실패!!")//다이얼로그 제목
            .setMessage("비밀번호가 잘못되었습니다.")//다이얼로그 메시지
            .setPositiveButton("확인") { _, _ -> }//다이얼로그 버튼
            .create()//다이얼로그 생성
            .show()//다이얼로그를 보여줌
    }
}