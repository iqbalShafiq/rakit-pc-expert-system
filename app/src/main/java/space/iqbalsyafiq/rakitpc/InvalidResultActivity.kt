package space.iqbalsyafiq.rakitpc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.iqbalsyafiq.rakitpc.databinding.ActivityInvalidResultBinding

class InvalidResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvalidResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvalidResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { onBackPressed() }
    }
}