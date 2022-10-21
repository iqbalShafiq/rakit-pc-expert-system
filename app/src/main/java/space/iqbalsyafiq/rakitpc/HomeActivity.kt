package space.iqbalsyafiq.rakitpc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.iqbalsyafiq.rakitpc.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            Intent(this, QuestionerActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }
    }
}