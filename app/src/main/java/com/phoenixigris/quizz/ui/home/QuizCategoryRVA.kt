package com.phoenixigris.quizz.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phoenixigris.quizz.databinding.QuizCatCardBinding
import com.phoenixigris.quizz.ui.home.model.QuizCategoryModel
import com.phoenixigris.quizz.utils.QuizLevelEnum

private const val TAG = "QuizCategoryRVA"

class QuizCategoryRVA(
    private val mList: List<QuizCategoryModel>,
    private val onBtnClicked: (QuizCategoryModel, QuizLevelEnum) -> Unit
) :
    RecyclerView.Adapter<QuizCategoryRVA.Holder>() {

    inner class Holder(private val binding: QuizCatCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var selectedLevel = QuizLevelEnum.EASY
        fun bindView(data: QuizCategoryModel, position: Int) {
            binding.quizCatNameTV.text = data.name
            binding.cateogoryLottie.setAnimation(data.imageResource)
            setClickListenerToBtn(position)
        }

        private fun setClickListenerToBtn(position: Int) {
            val data = mList[position]
            binding.root.setOnClickListener {
                onBtnClicked(data, selectedLevel)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizCategoryRVA.Holder {
        return Holder(
            QuizCatCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = mList[position]
        holder.bindView(data, position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}