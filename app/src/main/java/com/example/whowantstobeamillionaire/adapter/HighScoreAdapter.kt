package com.example.whowantstobeamillionaire.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whowantstobeamillionaire.databinding.ItemHighScoreBinding
import com.example.whowantstobeamillionaire.model.HighScore

class HighScoreAdapter : RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder> {
    private var inter: IHighScore

    constructor(inter:IHighScore) {
        this.inter = inter
    }
    interface IHighScore {
        fun getCount():Int
        fun getData(position: Int): HighScore
    }
    class HighScoreViewHolder(val binding: ItemHighScoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        return HighScoreViewHolder(
            ItemHighScoreBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        holder.binding.data = inter.getData(position)
        holder.binding.tvRank.text = (position+1).toString()
    }

    override fun getItemCount(): Int {
        return inter.getCount()
    }

}