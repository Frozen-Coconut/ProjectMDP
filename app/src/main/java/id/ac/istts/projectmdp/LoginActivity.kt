package id.ac.istts.projectmdp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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
                    var password = ""
                    val requestQueue = Volley.newRequestQueue(this)
                    val url = Connection.URL + "users/get?email=${binding.loginTxtEmail.text.toString()}"
                    val request = JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        { response ->
                            try {
                                password = response.getString("password")
                                if (binding.loginTxtPassword.text.toString() == password) {
                                    Toast.makeText(this@LoginActivity, "Berhasil login!", Toast.LENGTH_SHORT).show()
                                    // TODO: JANGAN LUPA DIARAHKAN KE MANA
                                } else {
                                    Toast.makeText(this@LoginActivity, "Password salah!", Toast.LENGTH_SHORT).show()
                                }
                                Log.d("Laravel", response.toString())
                            } catch (ex: Exception) {
                                Toast.makeText(this, "User tidak terdaftar!", Toast.LENGTH_SHORT).show()
                                Log.e("Laravel", ex.message.toString())
                            }
                        },
                        { error ->
                            Toast.makeText(this, "Gagal login!", Toast.LENGTH_SHORT).show()
                            Log.e("Laravel", error.toString())
                        }
                    )
                    requestQueue.add(request)
                }
            }
        }

        binding.loginBtnBack.setOnClickListener {
            finish()
        }
    }
}