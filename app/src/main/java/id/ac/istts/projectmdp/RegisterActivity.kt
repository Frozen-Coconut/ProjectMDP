package id.ac.istts.projectmdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ac.istts.projectmdp.databinding.ActivityMainBinding
import id.ac.istts.projectmdp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.registerBtnToLogin.setOnClickListener {
            finish()
        }
    }
}