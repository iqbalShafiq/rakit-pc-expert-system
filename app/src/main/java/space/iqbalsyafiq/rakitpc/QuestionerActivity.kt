package space.iqbalsyafiq.rakitpc

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import space.iqbalsyafiq.rakitpc.databinding.ActivityQuestionerBinding
import space.iqbalsyafiq.rakitpc.model.Question
import space.iqbalsyafiq.rakitpc.util.DataUtil.getJsonDataFromAsset

class QuestionerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonFileString = getJsonDataFromAsset(applicationContext, "questions.json")
        val listQuestionType = object : TypeToken<List<Question>>() {}.type
        val questions: List<Question> = Gson().fromJson(jsonFileString, listQuestionType)

        Log.d(TAG, "onCreate: $questions")
    }

    companion object {
        private val TAG = QuestionerActivity::class.java.simpleName
    }
}