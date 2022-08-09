package com.example.whowantstobeamillionaire.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.whowantstobeamillionaire.R
import com.example.whowantstobeamillionaire.databinding.MainFragmentBinding

class FirstFragment :BaseFragment(), View.OnClickListener {
    private lateinit var binding: MainFragmentBinding
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater,container,false)
        initData()
        binding.btnNewGame.setOnClickListener(this)
        binding.btnHighScore.setOnClickListener(this)
        return binding.root
    }

    private fun initData() {
        mediaPlayer = MediaPlayer.create(context, R.raw.bgmusic)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
        binding.animCircle.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bg_circle_rotate))
    }
    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_new_game -> {
                mediaPlayer.stop()
                mediaPlayer.release()
                mediaPlayer = MediaPlayer.create(context, R.raw.touch_sound)
                mediaPlayer.start()
                binding.btnNewGame.setBackgroundResource(R.drawable.bg_paly_press)
                Handler().postDelayed({
                    requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        QuestionFragment(),
                        QuestionFragment::class.java.name
                    )
                    .addToBackStack(null).commit()}, 500)
            }

            R.id.btn_high_score -> {
                mediaPlayer.release()
                binding.btnHighScore.setBackgroundResource(R.drawable.bg_hight_score_press)
                mediaPlayer = MediaPlayer.create(context, R.raw.touch_sound)
                mediaPlayer.start()
                Handler().postDelayed({
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            ScoreFragment(),
                            ScoreFragment::class.java.name
                        )
                        .addToBackStack(null).commit()
                },500)
            }
        }
    }
}