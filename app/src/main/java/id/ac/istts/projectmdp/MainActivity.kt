package id.ac.istts.projectmdp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.ac.istts.projectmdp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.mainBtnLogin.setOnClickListener {
            if (binding.mainTxtEmail.text.isEmpty() || binding.mainTxtPassword.text.isEmpty()) {
                Toast.makeText(this, "Semua input harus diisi!", Toast.LENGTH_SHORT).show()
            } else {

            }
        }

        binding.mainBtnToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}