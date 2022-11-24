package id.ac.istts.projectmdp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import id.ac.istts.projectmdp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.mainBtnToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.mainBtnLogin.setOnClickListener {
            val email = binding.mainTxtEmail.text.toString()
            val password = binding.mainTxtPassword.text.toString()

            if(email.isEmpty()){
                binding.mainTxtEmail.error = "email harus diisi"
                binding.mainTxtEmail.requestFocus()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.mainTxtEmail.error = "Email tidak valid"
                binding.mainTxtEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                binding.mainTxtPassword.error = "email harus diisi"
                binding.mainTxtPassword.requestFocus()
                return@setOnClickListener
            }
            loginauth(email, password)
        }
    }
    private fun loginauth(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if(it.isSuccessful){
                Toast.makeText(this, "Login Berhasil Welcom $email", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}