package space.iqbalsyafiq.rakitpc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import space.iqbalsyafiq.rakitpc.databinding.ActivityQuestionerBinding
import space.iqbalsyafiq.rakitpc.model.Question
import space.iqbalsyafiq.rakitpc.model.Rule
import space.iqbalsyafiq.rakitpc.util.DataUtil.getJsonDataFromAsset

class QuestionerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionerBinding
    private val answers = mutableListOf<Int>()
    private var questions = mutableListOf<Question>()
    private var rules = mutableListOf<Rule>()
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
                getCurrentQuestion()?.let { question ->
                    currentQuestionId = question.qn
                    answers.add(question.no)

                    if (currentQuestionId == "-") {
                        checkForwardChaining()
                    } else {
                        questionNumber++
                        showCurrentQuestion()
                    }
                } ?: run {
                    checkForwardChaining()
                }
            }

            btnYes.setOnClickListener {
                getCurrentQuestion()?.let { question ->
                    currentQuestionId = question.q
                    answers.add(question.yes)

                    if (currentQuestionId == "-") {
                        checkForwardChaining()
                    } else {
                        questionNumber++
                        showCurrentQuestion()
                    }
                } ?: run {
                    checkForwardChaining()
                }
            }
        }
    }

    private fun getCurrentQuestion(): Question? {
        return questions.firstOrNull { question ->
            question.id == currentQuestionId
        }
    }

    private fun showCurrentQuestion() {
        with(binding) {
            tvQuestionNumber.text = getString(
                R.string.question_number,
                questionNumber.toString()
            )

            tvQuestionContent.text = getCurrentQuestion()?.question
        }
    }

    private fun checkForwardChaining() {
        // read rules.json asset file
        val jsonFileString = getJsonDataFromAsset(applicationContext, "rules.json")
        val listRuleType = object : TypeToken<List<Rule>>() {}.type
        rules = Gson().fromJson(jsonFileString, listRuleType)

        // start forward chaining
        val answerOutput = answers.joinToString(" & ")
        Log.d(TAG, "checkForwardChaining: $answerOutput")
        val rule = rules.firstOrNull { rule ->
            rule.output == answerOutput
        }
        rule?.let {
            Intent(this, ResultActivity::class.java).apply {
                putExtra(ResultActivity.RESULT_EXTRA, rule)
                startActivity(this)
                finish()
            }
        } ?: run {
            Intent(this, InvalidResultActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    companion object {
        private val TAG = QuestionerActivity::class.java.simpleName
    }
}