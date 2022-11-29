package id.ac.istts.projectmdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.ac.istts.projectmdp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var type = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.rgType.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.rbUser -> {
                    type = 0
                    binding.registerTxtDate.visibility = View.VISIBLE
                    binding.registerTxtPhone.visibility = View.GONE
                }
                R.id.rbHealthcare -> {
                    type = 1
                    binding.registerTxtDate.visibility = View.GONE
                    binding.registerTxtPhone.visibility = View.VISIBLE
                }
            }
        }

        binding.registerBtnRegister.setOnClickListener {
            if (binding.registerTxtEmail.text.isEmpty() || binding.registerTxtPassword.text.isEmpty() || binding.registerTxtPasswordConfirmation.text.isEmpty() || binding.registerTxtName.text.isEmpty() || binding.registerTxtAddress.text.isEmpty() || (type == 0 && binding.registerTxtDate.text.isEmpty()) || (type == 1 && binding.registerTxtPhone.text.isEmpty())) {
                Toast.makeText(this, "Semua input harus diisi!", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtPassword.text.toString() != binding.registerTxtPasswordConfirmation.text.toString()) {
                Toast.makeText(this, "Password dan konfirmasi password tidak sama!", Toast.LENGTH_SHORT).show()
            } else {
                val requestQueue = Volley.newRequestQueue(this)
                val url = Connection.URL + "users/insert"
                val request = object: StringRequest(
                    Method.POST,
                    url,
                    object: Response.Listener<String> {
                        override fun onResponse(response: String) {
                            Toast.makeText(this@RegisterActivity, "Berhasil mendaftar!", Toast.LENGTH_SHORT).show()
                            Log.d("Laravel", response)
                        }
                    },
                    object: Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError?) {
                            Log.e("Laravel", error.toString())
                        }
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params: MutableMap<String, String> = HashMap()
                        params["email"] = binding.registerTxtEmail.text.toString()
                        params["password"] = binding.registerTxtPassword.text.toString()
                        params["name"] = binding.registerTxtName.text.toString()
                        params["address"] = binding.registerTxtAddress.text.toString()
                        if (type == 0) {
                            params["date_of_birth"] = binding.registerTxtDate.text.toString()
                        } else {
                            params["phone"] = binding.registerTxtPhone.text.toString()
                        }
                        return params
                    }
                }
                requestQueue.add(request)
            }
        }

        binding.registerBtnBack.setOnClickListener {
            finish()
        }
    }
}