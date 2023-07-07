package com.example.exersicemesure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exersicemesure.databinding.ActivityBmiBinding
import com.example.exersicemesure.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private var binding : ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.ToolBar)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }
        binding?.ToolBar?.setNavigationOnClickListener{
            onBackPressed()
        }
    }

}