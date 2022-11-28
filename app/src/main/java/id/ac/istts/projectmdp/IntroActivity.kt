package id.ac.istts.projectmdp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class IntroActivity : AppCompatActivity() {
    private lateinit var btnLogin : Button
    private lateinit var btnSignup : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        btnLogin = findViewById(R.id.intro_btnLogin)
        btnSignup = findViewById(R.id.intro_btnSignup)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}