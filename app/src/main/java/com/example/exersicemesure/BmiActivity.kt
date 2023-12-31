package com.example.exersicemesure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.exersicemesure.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {
    private var binding : ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.ToolBar)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }
        binding?.ToolBar?.setNavigationOnClickListener{
            onBackPressed()
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            if(validInput()){
                val heightValur : Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val weightValur : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val BMI =  weightValur/(heightValur*heightValur)
                displayBMIResult(BMI)
            }
            else{
                Toast.makeText(this,"Something is empty",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMIResult(bmi : Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Use to set the result layout visible
        binding?.llDiplayBMIResult?.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue // Value is set to TextView
        binding?.tvBMIType?.text = bmiLabel // Label is set to TextView
        binding?.tvBMIDescription?.text = bmiDescription // Description is set to TextView

    }

    private fun validInput() : Boolean
    {
        if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            return false
        }else if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            return false
        }else
        {
            return true
        }
    }
}