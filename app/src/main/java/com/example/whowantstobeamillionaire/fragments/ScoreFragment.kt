package com.example.whowantstobeamillionaire.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whowantstobeamillionaire.adapter.HighScoreAdapter
import com.example.whowantstobeamillionaire.databinding.FragmentHighScoreBinding
import com.example.whowantstobeamillionaire.manager.DatabaseManager
import com.example.whowantstobeamillionaire.model.HighScore

class ScoreFragment: BaseFragment(), HighScoreAdapter.IHighScore {
    private lateinit var binding: FragmentHighScoreBinding
    private var highScores = mutableListOf<HighScore>()
    private lateinit var databaseManager: DatabaseManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHighScoreBinding.inflate(inflater, container, false)
        initData()
        binding.rcListHighScore.layoutManager = LinearLayoutManager(context)
        binding.rcListHighScore.adapter = HighScoreAdapter(this)
        return binding.root
    }

    private fun initData() {
        databaseManager = DatabaseManager(requireContext())
        highScores.addAll(databaseManager.queryHighScore())
        Log.d("HighScore", highScores[0].toString())
        Log.d("HighScore", highScores[1].toString())
        Log.d("HighScore", highScores[2].toString())
        Log.d("HighScore", highScores[3].toString())
        Log.d("HighScore", highScores[4].toString())
        Log.d("HighScore", highScores[5].toString())
    }
    override fun getCount(): Int {
        return highScores.size
    }

    override fun getData(position: Int): HighScore {
        return highScores[position]
    }
}