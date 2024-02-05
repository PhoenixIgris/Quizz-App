package com.phoenixigris.quizz.ui.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
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
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.databinding.ActivityQuizBinding
import com.phoenixigris.quizz.network.reponse.QuestionAnswerListResponse
import com.phoenixigris.quizz.network.reponse.Result
import com.phoenixigris.quizz.ui.MainActivity
import com.phoenixigris.quizz.ui.home.model.QuizCategoryModel
import com.phoenixigris.quizz.ui.quiz.ui.theme.QuizzTheme
import com.phoenixigris.quizz.utils.IntentParams
import com.phoenixigris.quizz.utils.IntentParams.TRIVIA_QUESTIONS
import com.phoenixigris.quizz.utils.QuizLevelEnum
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "QuizActivity"

@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {

    private var selectModeDialog: AlertDialog? = null
    private lateinit var binding: ActivityQuizBinding
    private val viewModel: QuizActivityViewModel by viewModels()
    private lateinit var questionList: List<Result>
    private lateinit var currentQuesAnsSet: MutableState<Result>
    private var counter: MutableState<Int> = mutableStateOf(0)
    private var currentScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQuizBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        initialize()
        setContentView(binding.root)
    }

    private fun initialize() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        questionList = Gson().fromJson(
            intent.getStringExtra(TRIVIA_QUESTIONS),
            QuestionAnswerListResponse::class.java
        ).results ?: emptyList()
        if (questionList.isNotEmpty()) {
            currentQuesAnsSet = mutableStateOf(questionList[counter.value])
            binding.progressBar.progress = 0
            displayQuestionAnswer()
        } else {
            Toast.makeText(this, "There was problem fetching questions", Toast.LENGTH_SHORT).show()
            finish()
        }
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

            Log.d(TAG, "SetView:  $currentQuesAnsSet")
            Text(
                text = currentQuesAnsSet.value.question,
                Modifier
                    .layoutId("question")
                    .wrapContentWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                color = colorResource(id = R.color.black),
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
            val answerList = mutableListOf<String>()
            answerList.add(currentQuesAnsSet.value.correct_answer)
            answerList.addAll(currentQuesAnsSet.value.incorrect_answers)
            answerList.shuffle()
            val (selectedOption, onOptionSelected) = remember {
                mutableStateOf(
                    answerList.firstOrNull()
                )
            }
            for (answers in answerList) {
                currentQuesAnsSet.value.let {
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (answers == selectedOption),
                                onClick = {
                                    onOptionSelected(answers)
                                    addResult(answers)
                                }
                            )
                            .fillMaxWidth()
                            .background(
                                colorResource(id = R.color.white),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 2.dp, color = colorResource(
                                    id = R.color.gray_100,
                                ), shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 18.dp, vertical = 15.dp),
                    ) {
                        Text(
                            text = answers,
                            fontSize = 18.sp,
                            letterSpacing = 0.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Left,
                            color = Color.Black,
                            modifier = Modifier.align(CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.padding(bottom = 15.dp))
                }
            }
        }
    }

    private fun addResult(answer: String) {
        if (currentQuesAnsSet.value.correct_answer == answer) {
            currentScore += 1
        }
        Log.d(TAG, "addResult: score $currentScore")
        showNextQuesAns()
    }

    private fun showNextQuesAns() {
        if (counter.value >= questionList.size - 1) {
            val data =
                intent.getStringExtra(IntentParams.SELECTED_CATEGORY)
            binding.progressBar.progress = 100
            binding.count.text = (counter.value + 1).toString()
            viewModel.storeResult(
                Gson().fromJson(
                    data,
                    QuizCategoryModel::class.java
                ),
                intent.getStringExtra(IntentParams.SELECTED_LEVEL) ?: QuizLevelEnum.EASY.name,
                currentScore
            )

            showResult(currentScore)

        } else {
            counter.value++
            currentQuesAnsSet.value = questionList[counter.value]
            binding.progressBar.progress =
                (((counter.value.toFloat()) / questionList.size.toFloat()) * 100F).toInt()
            binding.count.text = counter.value.toString()
        }
    }

    private fun showResult(score: Int) {
        selectModeDialog =
            AlertDialog.Builder(this, R.style.CustomDialogTheme).apply {
                setCancelable(false)
                setView(
                    LayoutInflater.from(context).inflate(R.layout.result_view_lyt, null)
                        .apply {
                            findViewById<TextView>(R.id.score).text = score.toString()
                            findViewById<LottieAnimationView>(R.id.progress_circular).progress =
                                ((score.toFloat()) / questionList.size.toFloat())
                            findViewById<MaterialButton>(R.id.playAgain).setOnClickListener {
                                selectModeDialog?.dismiss()
                                this@QuizActivity.recreate()
                            }
                            findViewById<MaterialButton>(R.id.close).setOnClickListener {
                                selectModeDialog?.dismiss()
                                startActivity(Intent(this@QuizActivity, MainActivity::class.java))
                            }
                        })
            }.create()
        selectModeDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.let {
                it.setGravity(Gravity.CENTER)
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(it.attributes)
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                layoutParams.width = (resources.displayMetrics.widthPixels * 0.85).toInt()
                it.attributes = layoutParams
            }
        }
        selectModeDialog?.dismiss()
        selectModeDialog?.show()
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        QuizzTheme {
            SetView()
        }
    }


}