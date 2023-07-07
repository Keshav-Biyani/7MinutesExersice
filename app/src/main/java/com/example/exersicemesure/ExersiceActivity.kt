package com.example.exersicemesure

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exersicemesure.databinding.ActivityExersiceBinding
import com.example.exersicemesure.databinding.DialogCustomBackConfirmationBinding
import java.lang.Exception
import java.util.Locale

class ExersiceActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var binding : ActivityExersiceBinding? = null
    var restprogress =0
    var restTimer : CountDownTimer? = null
    var restprogressExersicse =0
    var restTimerExersicse : CountDownTimer? = null
    var Exersicelist : ArrayList<ExerciseModel>? = null
    var ExersiceNumber : Int= -1
   private var tts : TextToSpeech? = null
    private var player : MediaPlayer? = null
    private var exersciseAdapter : ExersciseStatusAdapter? = null
    private var custDialog : Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExersiceBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.ToolBar)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        Exersicelist = Constaints.getEcersice()
        tts = TextToSpeech(this,this)
        binding?.ToolBar?.setNavigationOnClickListener{
            customDialog()
        }

setProgressView()
        setupRecycleView()
    }

    private fun setExersiceProgressView(){
        onSpeak(Exersicelist!![ExersiceNumber].getName())
        binding?.flProgress?.visibility = View.INVISIBLE
        binding?.flProgressExerscise?.visibility = View.VISIBLE
        binding?.tvReady?.visibility = View.INVISIBLE
        binding?.tvExersciseName?.visibility = View.VISIBLE
        binding?.IVimageExersice?.visibility = View.VISIBLE
        binding?.tvUpcommingExercise?.visibility = View.INVISIBLE
        binding?.tvExersciseName?.text = Exersicelist!![ExersiceNumber].getName()
        binding?.IVimageExersice?.setImageResource(Exersicelist!![ExersiceNumber].getImage())
        Exersicelist!![ExersiceNumber].setIsSelected(true)
        exersciseAdapter!!.notifyDataSetChanged()


        if(restTimerExersicse != null) {
            restTimerExersicse?.cancel()
            restprogressExersicse = 0
        }
        setProgressTimmerExersice()


    }
    @SuppressLint("NotifyDataSetChanged")
    private  fun setProgressView(){// seting it for suring of starting with the timer 0

        try {

            player = MediaPlayer.create(applicationContext,R.raw.press_start)
            player?.start()
            player?.isLooping = false
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        binding?.flProgress?.visibility = View.VISIBLE
        binding?.flProgressExerscise?.visibility = View.INVISIBLE
        binding?.tvReady?.visibility = View.VISIBLE
        binding?.tvExersciseName?.visibility = View.INVISIBLE
        binding?.IVimageExersice?.visibility = View.INVISIBLE
        binding?.tvUpcommingExercise?.visibility = View.VISIBLE
        binding?.tvUpcommingExercise?.text = Exersicelist!![ExersiceNumber+1].getName()
        if(ExersiceNumber >= 0) {
            Exersicelist!![ExersiceNumber].setIsSelected(false)
            Exersicelist!![ExersiceNumber].setIsComplted(true)
            exersciseAdapter!!.notifyDataSetChanged()
        }
        if(restTimer != null) {
            restTimer?.cancel()
            restprogress = 0
        }

        setProgressTimmer()

    }
    private  fun  setProgressTimmer(){

        restTimer = object : CountDownTimer(5000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restprogress++
                binding?.ProgreesBar?.progress = 10-restprogress
                binding?.tvtimer?.text = (10-restprogress).toString()
            }

            override fun onFinish() {
                ExersiceNumber++

                Toast.makeText(this@ExersiceActivity,"Here you are set to do exercise",Toast.LENGTH_SHORT).show()
                setExersiceProgressView()
            }
        }.start()

    }
    private fun setupRecycleView(){
        binding?.rvExersicseStatus?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exersciseAdapter = ExersciseStatusAdapter(Exersicelist!!)
        binding?.rvExersicseStatus?.adapter = exersciseAdapter
    }
    private  fun  setProgressTimmerExersice(){

        restTimerExersicse = object : CountDownTimer(5000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restprogressExersicse++
                binding?.ProgreesBarExersice?.progress = 30-restprogressExersicse
                binding?.tvtimerExersice?.text = (30-restprogressExersicse).toString()
            }

            override fun onFinish() {
                if(ExersiceNumber < (Exersicelist!!.size -1)) {
                    setProgressView()
                }else {

                    val intent = Intent(this@ExersiceActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
               // Toast.makeText(this@ExersiceActivity,"Here you are set to do exercise",Toast.LENGTH_SHORT).show()
            }
        }.start()

    }
    private fun customDialog(){
        custDialog = Dialog(this@ExersiceActivity)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        custDialog?.setContentView(dialogBinding.root)
        custDialog?.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExersiceActivity.finish()
            custDialog?.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            custDialog?.dismiss()
        }
        custDialog?.show()


    }
    override fun onDestroy() {

        super.onDestroy()
        if(player != null){
            player?.stop()
            player?.release()
            player = null
        }
        if(restTimer != null) {
            restTimer?.cancel()
            restprogress = 0
        }
        if(restTimerExersicse != null) {
            restTimerExersicse?.cancel()
            restprogressExersicse = 0
        }
        if(tts != null){
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }

    override fun onInit(status: Int) {

        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.UK)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("tts","Error in result")
            }

        }else{
            Log.e("tts","error in status")
        }

    }
    fun onSpeak(exersicename : String){
        tts?.speak(exersicename,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}