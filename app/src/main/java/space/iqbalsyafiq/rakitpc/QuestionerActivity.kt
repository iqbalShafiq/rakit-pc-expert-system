package space.iqbalsyafiq.rakitpc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import space.iqbalsyafiq.rakitpc.databinding.ActivityQuestionerBinding
import space.iqbalsyafiq.rakitpc.model.Question
import space.iqbalsyafiq.rakitpc.util.DataUtil.getJsonDataFromAsset

class QuestionerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionerBinding
    private val answers = mutableListOf<Int>()
    private var questions = mutableListOf<Question>()
    private var questionNumber = 1
    private var currentQuestionId = "Q1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set toolbar
        binding.toolbar.ivBack.setOnClickListener { onBackPressed() }
        binding.toolbar.tvTitle.text = getString(R.string.questioner)

        // read questions.json asset file
        val jsonFileString = getJsonDataFromAsset(applicationContext, "questions.json")
        val listQuestionType = object : TypeToken<List<Question>>() {}.type
        questions = Gson().fromJson(jsonFileString, listQuestionType)

        // show questions to screen
        with(binding) {
            showCurrentQuestion()

            btnNo.setOnClickListener {
                currentQuestionId = getCurrentQuestion().qn
                answers.add(getCurrentQuestion().no)
                questionNumber++
                showCurrentQuestion()
            }

            btnYes.setOnClickListener {
                currentQuestionId = getCurrentQuestion().q
                answers.add(getCurrentQuestion().yes)
                questionNumber++
                showCurrentQuestion()
            }
        }
    }

    private fun getCurrentQuestion(): Question {
        return questions.first { question ->
            question.id == currentQuestionId
        }
    }

    private fun showCurrentQuestion() {
        with(binding) {
            tvQuestionNumber.text = getString(
                R.string.question_number,
                questionNumber.toString()
            )

            tvQuestionContent.text = getCurrentQuestion().question
        }
    }

    companion object {
        private val TAG = QuestionerActivity::class.java.simpleName
    }
}