package com.phoenixigris.quizz.ui.quiz

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.databinding.ActivityQuizBinding
import com.phoenixigris.quizz.helpers.Constants.QUIZ_TYPE
import com.phoenixigris.quizz.helpers.HelperClass.linearGradientDrawable
import com.phoenixigris.quizz.helpers.QuizTypeEnum
import com.phoenixigris.quizz.network.reponse.Answers
import com.phoenixigris.quizz.network.reponse.CorrectAnswers
import com.phoenixigris.quizz.network.reponse.QuestionResponseItem
import com.phoenixigris.quizz.ui.quiz.ui.theme.QuizzTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.full.memberProperties

private const val TAG = "QuizActivity"

@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val viewModel: QuizActivityViewModel by viewModels()
    private lateinit var questionList: List<QuestionResponseItem>
    private lateinit var currentQuesAnsSet: MutableState<QuestionResponseItem>
    private var counter: MutableState<Int> = mutableStateOf(0)
    private var currentScore: Int = 0
    private lateinit var quizTypeEnum: QuizTypeEnum

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQuizBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        initialize()
        setContentView(binding.root)
    }

    private fun initialize() {
        quizTypeEnum = intent.getSerializableExtra(QUIZ_TYPE) as QuizTypeEnum
        observeQuestion(quizTypeEnum)
        setBackGround()
    }


    private fun observeQuestion(quizTypeEnum: QuizTypeEnum) {
        viewModel.getQuestionList(quizTypeEnum)
            .observe(this) {
                questionList = it
                currentQuesAnsSet = mutableStateOf(it[counter.value])
                displayQuestionAnswer()
            }

    }

    private fun setBackGround() {
        binding.root.background = linearGradientDrawable(
            "#A3A5DA", "#FAF5FB", "#F7EAFD",
            GradientDrawable.Orientation.BOTTOM_TOP
        )
    }

    private fun displayQuestionAnswer() {
        binding.actQuizContainer.apply {
            setContent {
                QuizzTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        color = Color.Transparent,
                    ) {
                        SetView()
                    }
                }
            }
        }
    }

    @Composable
    private fun SetView() {
        ConstraintLayout(
            quesAnsConstrains(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = currentQuesAnsSet.value.question,
                Modifier.layoutId("question"),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Left,
                color = colorResource(id = R.color.gray_700),
                letterSpacing = 0.5.sp
            )
            AddAnswerButtons()
        }
    }


    private fun quesAnsConstrains(): ConstraintSet {
        return ConstraintSet {
            val question = createRefFor("question")
            val answerBtn = createRefFor("AnswerBtn")
            constrain(question) {
                absoluteLeft.linkTo(parent.absoluteLeft)
                absoluteRight.linkTo(parent.absoluteRight)
            }
            constrain(answerBtn) {
                absoluteLeft.linkTo(parent.absoluteLeft)
                absoluteRight.linkTo(parent.absoluteRight)
            }
            createVerticalChain(question, answerBtn, chainStyle = ChainStyle.Spread)
        }
    }


    @Composable
    fun AddAnswerButtons() {
        Column(
            Modifier
                .fillMaxWidth()
                .layoutId("AnswerBtn")
        ) {
            val answerList = Answers::class.memberProperties
            val (selectedOption, onOptionSelected) = remember {
                mutableStateOf(
                    answerList.first().get(currentQuesAnsSet.value.answers)
                )
            }
            for (answers in answerList) {
                currentQuesAnsSet.value.let { questionAnswerSet ->
                    val answer = answers.get(questionAnswerSet.answers)
                    if (answer != null) {
                        Row(
                            modifier = Modifier
                                .selectable(
                                    selected = (answer.toString() == selectedOption),
                                    onClick = {
                                        onOptionSelected(answer.toString())
                                        addResult(answers.name)
                                    }
                                )
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF8786EB),
                                            Color(0xFFBE9CF3),
                                            Color(0xFFE59FF1)
                                        )
                                    ),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                        ) {
                            RadioButton(
                                selected = (answer.toString() === selectedOption),
                                onClick = { onOptionSelected(answer.toString()) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.White,
                                    unselectedColor = Color.White,
                                    disabledColor = Color.White
                                )
                            )
                            Text(
                                text = answer.toString(),
                                fontSize = 18.sp,
                                letterSpacing = 0.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Left,
                                color = Color.White,
                                modifier = Modifier.align(CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.padding(bottom = 15.dp))
                    }
                }
            }
        }
    }

    private fun addResult(answer: String) {
        val correctAnswerList = CorrectAnswers::class.memberProperties
        for (correctAnswer in correctAnswerList) {
            val correctAnswerValue = correctAnswer.get(currentQuesAnsSet.value.correct_answers)
            if (correctAnswerValue.toString() == "true") {
                Log.e(TAG, "addResult: ${correctAnswer.name} $answer")
                when (correctAnswer.name) {
                    CorrectAnswers::answer_a_correct.name -> {
                        if (answer == Answers::answer_a.name) {
                            currentScore += 1
                            break
                        }
                    }
                    CorrectAnswers::answer_b_correct.name -> {
                        if (answer == Answers::answer_b.name) {
                            currentScore += 1
                            break
                        }
                    }
                    CorrectAnswers::answer_c_correct.name -> {
                        if (answer == Answers::answer_c.name) {
                            currentScore += 1
                            break
                        }
                    }
                    CorrectAnswers::answer_d_correct.name -> {
                        if (answer == Answers::answer_d.name) {
                            currentScore += 1
                            break
                        }
                    }
                    CorrectAnswers::answer_e_correct.name -> {
                        if (answer == Answers::answer_e.name) {
                            currentScore += 1
                            break
                        }
                    }
                    CorrectAnswers::answer_f_correct.name -> {
                        if (answer == Answers::answer_f.name) {
                            currentScore += 1
                            break
                        }
                    }
                }
            }
        }
        Log.e(TAG, "addResult: $currentScore")

        showNextQuesAns()
    }

    private fun showNextQuesAns() {
        if (counter.value >= questionList.size - 1) {
            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show()
            binding.progressBar.progress = 100
            viewModel.storeResult(quizTypeEnum, currentScore)
            this.finish()
        } else {
            counter.value++
            currentQuesAnsSet.value = questionList[counter.value]
            binding.progressBar.progress =
                (((counter.value.toFloat()) / questionList.size.toFloat()) * 100F).toInt()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        QuizzTheme {
            SetView()
        }
    }
}