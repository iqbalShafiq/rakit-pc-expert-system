package space.iqbalsyafiq.rakitpc

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import space.iqbalsyafiq.rakitpc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        skipSplashScreen()
    }

    /**
     * Waiting 1000 second and move from splash screen
     */
    private fun skipSplashScreen() {
        runBlocking {
            lifecycleScope.launch {
                delay(1000)

                Intent(
                    this@MainActivity,
                    HomeActivity::class.java
                ).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }
}