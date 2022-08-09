package com.example.whowantstobeamillionaire.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.whowantstobeamillionaire.R
import com.example.whowantstobeamillionaire.databinding.LayoutMoneyBinding
import com.example.whowantstobeamillionaire.databinding.QuestionFragmentBinding


class QuestionFragment: BaseFragment(), View.OnClickListener {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var binding: LayoutMoneyBinding
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutMoneyBinding.inflate(inflater,container,false)
        initData()
        binding.continues.setOnClickListener(this)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    private fun initData() {
        mediaPlayer = MediaPlayer.create(context, R.raw.luatchoi_b)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { openDialog() }
        Handler().postDelayed({binding.tvLevel5.setBackgroundResource(R.drawable.player_image_money_curent)}, 4600)
        Handler().postDelayed({
            binding.tvLevel5.setBackgroundResource(0)
            binding.tvLevel10.setBackgroundResource(R.drawable.player_image_money_curent)}, 5100)
        Handler().postDelayed({
            binding.tvLevel10.setBackgroundResource(0)
            binding.tvLevel15.setBackgroundResource(R.drawable.player_image_money_curent)}, 5600)
    }
    private fun stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlayer()
    }

    private fun openDialog() {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_demo)
        dialog.setTitle(R.string.dialog_title)
        dialog.show()
        var buttonOk: Button = dialog.findViewById(R.id.dialog_ok)
        var buttonCancel :Button = dialog.findViewById(R.id.dialog_cancel)
        buttonOk.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id) {
                R.id.continues->{
                    openDialog()
                    stopPlayer()
                }
                R.id.dialog_ok->{
                    activity?.supportFragmentManager?.beginTransaction()?.replace(
                        R.id.fragment_container,
                        PlayerFragment(),
                        PlayerFragment::class.java.name
                    )
                        ?.addToBackStack(null)?.commit()
                    dialog.cancel()// Context, this, etc.
                    Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show()
                }
                R.id.dialog_cancel->{
                    Toast.makeText(context,"cancel",Toast.LENGTH_SHORT).show()
                    onFinish1()
                }
            }
        }
    }
}