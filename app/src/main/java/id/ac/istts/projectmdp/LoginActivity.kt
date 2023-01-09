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
import id.ac.istts.projectmdp.hospital.PuskesmasActivity
import id.ac.istts.projectmdp.user.UserActivity

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
            } else if (binding.loginTxtEmail.text.toString() == "admin@example.com" && binding.loginTxtPassword.text.toString() == "moc.elpmaxe@nimda") {
                startActivity(Intent(this, AdminActivity::class.java))
            } else {
                var password: String
                val requestQueue = Volley.newRequestQueue(this)
                val url = Connection.URL + "users/get?email=${binding.loginTxtEmail.text}"
                val request = JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    { response ->
                        try {
                            password = response.getString("password")
                            if (binding.loginTxtPassword.text.toString() == password) {
                                Connection.current = response.getInt("id")
                                Connection.email = response.getString("email")
                                Toast.makeText(this@LoginActivity, "Berhasil login!", Toast.LENGTH_SHORT).show()
                                if (response.getString("date_of_birth") == "null") {
                                    startActivity(Intent(this, PuskesmasActivity::class.java))
                                } else {
                                    startActivity(Intent(this, UserActivity::class.java))
                                }
                                binding.loginTxtEmail.text.clear()
                                binding.loginTxtPassword.text.clear()
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

        binding.loginBtnBack.setOnClickListener {
            finish()
        }
    }
}