package id.ac.istts.projectmdp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.ac.istts.projectmdp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginBtnLogin.setOnClickListener {
            if (binding.loginTxtEmail.text.isEmpty() || binding.loginTxtPassword.text.isEmpty()) {
                Toast.makeText(this, "Semua input harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.loginTxtEmail.text.toString() == "admin" && binding.loginTxtPassword.text.toString() == "admin") {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    
                }
            }
        }

        binding.loginBtnBack.setOnClickListener {
            finish()
        }
    }
}