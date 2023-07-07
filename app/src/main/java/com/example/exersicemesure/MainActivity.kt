package com.example.exersicemesure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.exersicemesure.databinding.ActivityMainBinding
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    var binding : ActivityMainBinding? =null//ViewBinding is a feature introduced by Google in Android development to simplify the process of accessing views in your application's layout files. It generates a binding class for each XML layout file at compile time, which allows you to directly reference views without using findViewById().
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)// for inflating the layout with the view
        setContentView(binding?.root)
       // val frameLayout : FrameLayout = findViewById(R.id.frameLayout)
        binding?.frameLayout?.setOnClickListener{
            val intent = Intent(this , ExersiceActivity::class.java)
            startActivity(intent)
        }
        binding?.frameLayoutBMI?.setOnClickListener{
            val intent = Intent(this , BmiActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onDestroy() {

        super.onDestroy()
        binding = null
    }
}