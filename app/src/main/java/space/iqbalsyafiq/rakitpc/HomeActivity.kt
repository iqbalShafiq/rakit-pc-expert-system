package space.iqbalsyafiq.rakitpc

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.iqbalsyafiq.rakitpc.databinding.ActivityHomeBinding
import space.iqbalsyafiq.rakitpc.model.Question
import space.iqbalsyafiq.rakitpc.model.Rule

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(MainActivity.EXTRA_QUESTIONS, Question::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(MainActivity.EXTRA_QUESTIONS)
        }

        val rules = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(MainActivity.EXTRA_RULES, Rule::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(MainActivity.EXTRA_RULES)
        }

        binding.btnStart.setOnClickListener {
            Intent(this, QuestionerActivity::class.java).apply {
                putExtra(MainActivity.EXTRA_QUESTIONS, questions)
                putExtra(MainActivity.EXTRA_RULES, rules)
                startActivity(this)
            }
        }

        binding.btnUpdate.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }
}