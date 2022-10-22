package space.iqbalsyafiq.rakitpc

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.iqbalsyafiq.rakitpc.databinding.ActivityMainBinding
import space.iqbalsyafiq.rakitpc.model.network.ApiService
import space.iqbalsyafiq.rakitpc.model.network.DataResponse
import space.iqbalsyafiq.rakitpc.util.DataUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        apiService = ApiService()
        skipSplashScreen()
    }

    /**
     * Waiting 1000 second and move from splash screen
     */
    private fun skipSplashScreen() {
        runBlocking {
            lifecycleScope.launch {
                fetchQuestionsData()
            }
        }
    }

    private fun fetchQuestionsData() {
        apiService.getQuestionList().enqueue(object : Callback<DataResponse> {
            override fun onResponse(
                call: Call<DataResponse>,
                response: Response<DataResponse>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                response.body()?.let { questions ->
                    fetchRulesData(questions)
                } ?: run {
                    Toast.makeText(
                        this@MainActivity,
                        "Gagal memuat data",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                t.printStackTrace()

                Toast.makeText(
                    this@MainActivity,
                    "Gagal memuat data",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        })
    }

    private fun fetchRulesData(questions: DataResponse) {
        apiService.getRuleList().enqueue(object : Callback<DataResponse> {
            override fun onResponse(
                call: Call<DataResponse>,
                response: Response<DataResponse>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")

                response.body()?.let { rules ->
                    Intent(
                        this@MainActivity,
                        HomeActivity::class.java
                    ).apply {
                        putExtra(
                            EXTRA_QUESTIONS,
                            DataUtil.convertDataToQuestions(questions) as ArrayList<out Parcelable>
                        )
                        putParcelableArrayListExtra(
                            EXTRA_RULES,
                            DataUtil.convertDataToRules(rules) as ArrayList<out Parcelable>
                        )
                        startActivity(this)
                        finish()
                    }
                } ?: run {
                    Toast.makeText(
                        this@MainActivity,
                        "Gagal memuat data",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                t.printStackTrace()

                Toast.makeText(
                    this@MainActivity,
                    "Gagal memuat data",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        })
    }

    companion object {
        private val TAG = ActivityMainBinding::class.java.simpleName
        const val EXTRA_QUESTIONS = "Questions Data"
        const val EXTRA_RULES = "Rules Data"
    }
}