package space.iqbalsyafiq.rakitpc

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.iqbalsyafiq.rakitpc.databinding.ActivityResultBinding
import space.iqbalsyafiq.rakitpc.model.Rule

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set toolbar
        binding.toolbar.ivBack.setOnClickListener { onBackPressed() }
        binding.toolbar.tvTitle.text = getString(R.string.result)

        val productResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(RESULT_EXTRA, Rule::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(RESULT_EXTRA)
        }

        showResult(productResult)
    }

    private fun showResult(productResult: Rule?) {
        productResult?.let { result ->
            with(binding) {
                tvMotherboardComponent.text = result.motherboard
                tvProcessorComponent.text = result.cpu
                tvRamComponent.text = result.ram
                tvStorageComponent.text = result.storage
                tvPowerSupplyComponent.text = result.powerSupply
                tvGpuComponent.text = result.gpu
                tvCoolerComponent.text = result.coolerCPU
                tvCasingComponent.text = result.casing
                tvTotalPrice.text = getString(R.string.pricing, result.totalPrice)
                tvDescription.text = "Coming Soon ..."
            }
        }
    }

    companion object {
        const val RESULT_EXTRA = "Result"
    }
}