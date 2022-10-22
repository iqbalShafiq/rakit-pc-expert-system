package space.iqbalsyafiq.rakitpc

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import space.iqbalsyafiq.rakitpc.databinding.ActivityQuestionerBinding
import space.iqbalsyafiq.rakitpc.model.Question
import space.iqbalsyafiq.rakitpc.model.Rule

class QuestionerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionerBinding
    private val answers = mutableListOf<String>()
    private var questions: java.util.ArrayList<Question> = arrayListOf()
    private var rules: java.util.ArrayList<Rule> = arrayListOf()
    private var questionNumber = 1
    private var currentQuestionId = "Q1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set toolbar
        binding.toolbar.ivBack.setOnClickListener { onBackPressed() }
        binding.toolbar.tvTitle.text = getString(R.string.questioner)

        // get questios and rules by intent
        questions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(MainActivity.EXTRA_QUESTIONS, Question::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(MainActivity.EXTRA_QUESTIONS)!!
        }

        rules = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(MainActivity.EXTRA_RULES, Rule::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(MainActivity.EXTRA_RULES)!!
        }

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