package com.phoenixigris.quizz.ui.quiz

import androidx.lifecycle.*
import com.phoenixigris.quizz.helpers.QuizModel
import com.phoenixigris.quizz.helpers.QuizTypeEnum
import com.phoenixigris.quizz.helpers.Score
import com.phoenixigris.quizz.network.reponse.QuestionResponseItem
import com.phoenixigris.quizz.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizActivityViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
) : ViewModel() {


    fun getQuestionList(quizType: QuizTypeEnum): LiveData<List<QuestionResponseItem>> {
        when (quizType) {
            QuizTypeEnum.LINUX -> {
                return quizRepository.getLinuxQuestion().asLiveData()
            }
            QuizTypeEnum.DEVOPS -> {
                return quizRepository.getDevOpsQuestionList().asLiveData()
            }
            QuizTypeEnum.DOCKER -> {
                return quizRepository.getDockerQuestionList().asLiveData()
            }
            QuizTypeEnum.SQL -> {
                return quizRepository.getSqlQuestionList().asLiveData()
            }
            QuizTypeEnum.CODE -> {
                return quizRepository.getCodeQuestionList().asLiveData()
            }
            QuizTypeEnum.CMS -> {
                return quizRepository.getCmsQuestionList().asLiveData()
            }
        }
    }

    fun storeResult(quizTypeEnum: QuizTypeEnum, score: Int) {
        viewModelScope.launch {
            val newList = mutableListOf<QuizModel>()
            quizRepository.getQuizStatusList().first().forEach {
                if (quizTypeEnum == it.quizType) {
                    newList.add(
                        QuizModel(
                            name = it.name,
                            status = it.status,
                            quizType = it.quizType,
                            score = Score(
                                easyScore = score,
                                mediumScore = it.score.mediumScore,
                                hardScore = it.score.hardScore
                            ),
                            drawableId = it.drawableId
                        )
                    )
                } else {
                    newList.add(it)
                }
            }
            quizRepository.setQuizStatusList(newList)
        }
    }
}