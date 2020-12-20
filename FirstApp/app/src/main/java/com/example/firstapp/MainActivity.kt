package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var  dogList = arrayListOf<Dog>(
        Dog("Chow Chow", "Male", "4"),
        Dog("Breed Pomeranian", "Female", "1"),
        Dog("Golden Retriver", "Female", "3"),
        Dog("Yorkshire Terrier", "Male", "5"),
        Dog("Pug", "Male", "4"),
        Dog("Alaskan Malamute", "Male", "7"),
        Dog("Shih Tzu", "Female", "5")
    )
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_move.setOnClickListener{
            var str=et_id.getText().toString()
            var intent=Intent(this,SubAcitivity::class.java)
            intent.putExtra("str",str)
            startActivity(intent)
            
        }
        test.setOnClickListener{
            Toast.makeText(this@MainActivity, "토스트 메세지 띄우기 입니다.", Toast.LENGTH_SHORT).show()

        }
        val dogAdapter=MainListAdapter(this,dogList)
        mainListView.adapter=dogAdapter

    }
}