package com.phoenixigris.quizz.ui.home

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.databinding.QuizCatCardBinding
import com.phoenixigris.quizz.utils.HelperClass
import com.phoenixigris.quizz.utils.QuizLevelEnum
import com.phoenixigris.quizz.utils.QuizModel
import com.phoenixigris.quizz.utils.QuizStatus

private const val TAG = "QuizCategoryRVA"

class QuizCategoryRVA(
    private val mList: List<QuizModel>,
    private val onBtnClicked: (QuizModel, QuizLevelEnum) -> Unit
) :
    RecyclerView.Adapter<QuizCategoryRVA.Holder>() {

    inner class Holder(private val binding: QuizCatCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var selectedLevel = QuizLevelEnum.EASY
        fun bindView(data: QuizModel, position: Int) {
            binding.quizCatNameTV.text = data.name
            setLevelView(position)
            setCardGradient()
            setClickListenerToBtn(position)
        }

        private fun setClickListenerToBtn(position: Int) {
            val data = mList[position]
            binding.quizCatStatusIV.setOnClickListener {
                onBtnClicked(data, selectedLevel)
            }
        }

        private fun setCardGradient() {
            binding.quizCatCard.apply {
                background = HelperClass.linearGradientDrawable(
                    "#5C71E4",
                    "#4C93EE",
                    "#55B9F9",
                    GradientDrawable.Orientation.LEFT_RIGHT, 60F
                )
            }
        }

        private fun setLevelView(position: Int) {
            val data = mList[position]
            binding.quizCatEasyStatusTV.setOnClickListener {
                if (data.status.easy == QuizStatus.INCOMPLETE) {
                    binding.quizCatStatusIV.setImageResource(R.drawable.ic_play)
                } else {
                    binding.quizCatStatusIV.setImageResource(R.drawable.ic_tick)
                }
                selectedLevel = QuizLevelEnum.EASY
            }
            binding.quizCatMediumStatusTV.setOnClickListener {
                if (data.status.easy == QuizStatus.COMPLETED) {
                    if (data.status.medium == QuizStatus.INCOMPLETE) {
                        binding.quizCatStatusIV.setImageResource(R.drawable.ic_play)
                    } else {
                        binding.quizCatStatusIV.setImageResource(R.drawable.ic_tick)
                    }
                } else {
                    binding.quizCatStatusIV.setImageResource(R.drawable.ic_locked)
                }
                selectedLevel = QuizLevelEnum.MEDIUM
            }
            binding.quizCatHardStatusTV.setOnClickListener {
                if (data.status.easy == QuizStatus.COMPLETED && data.status.medium == QuizStatus.COMPLETED) {
                    if (data.status.hard == QuizStatus.INCOMPLETE) {
                        binding.quizCatStatusIV.setImageResource(R.drawable.ic_play)
                    } else {
                        binding.quizCatStatusIV.setImageResource(R.drawable.ic_tick)
                    }
                } else {
                    binding.quizCatStatusIV.setImageResource(R.drawable.ic_locked)
                }
                selectedLevel = QuizLevelEnum.HARD

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