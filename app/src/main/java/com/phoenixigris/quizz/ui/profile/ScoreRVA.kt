package com.phoenixigris.quizz.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phoenixigris.quizz.databinding.ScoreCardBinding
import com.phoenixigris.quizz.helpers.QuizModel

class ScoreRVA(private val mList: List<QuizModel>) :
    RecyclerView.Adapter<ScoreRVA.Holder>() {

    inner class Holder(private val binding: ScoreCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(data: QuizModel, position: Int) {
            binding.scoreTypeTv.text = data.name
            binding.scoreValueTv.text = "${data.score.easyScore}/20"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreRVA.Holder {
        return Holder(
            ScoreCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ScoreRVA.Holder, position: Int) {
        val data = mList[position]
        holder.bindView(data, position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}