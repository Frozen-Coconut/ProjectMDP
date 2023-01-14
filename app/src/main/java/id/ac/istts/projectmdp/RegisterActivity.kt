package id.ac.istts.projectmdp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import id.ac.istts.projectmdp.databinding.ActivityRegisterBinding
import org.json.JSONObject
import java.net.URLEncoder

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var type = 0

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
                var position: LatLng? = null

                val anotherUrl = "https://geocode.search.hereapi.com/v1/geocode?q=" + URLEncoder.encode(binding.registerTxtAddress.text.toString(), "UTF-8") + "&apiKey=whXA9dgTB1kCYoeqesmMxMGhFTZrmz3FNK70aHbRF88"
                val anotherRequest = JsonObjectRequest(
                    Request.Method.GET,
                    anotherUrl,
                    null,
                    { anotherResponse ->
                        Log.d("Laravel", anotherResponse.toString())
                        val home = (anotherResponse.getJSONArray("items")[0] as JSONObject).getJSONObject("position")
                        position = LatLng(home.getDouble("lat"), home.getDouble("lng"))

                        val url = Connection.URL + "users/insert"
                        val request = object: StringRequest(
                            Method.POST,
                            url,
                            object: Response.Listener<String> {
                                override fun onResponse(response: String) {
                                    Toast.makeText(this@RegisterActivity, "Berhasil mendaftar!", Toast.LENGTH_SHORT).show()
                                    Log.d("Laravel", response)
                                    finish()
                                }
                            },
                            object: Response.ErrorListener {
                                override fun onErrorResponse(error: VolleyError?) {
                                    Toast.makeText(this@RegisterActivity, "Gagal mendaftar!", Toast.LENGTH_SHORT).show()
                                    Log.e("Laravel", error.toString())
                                    binding.registerTxtPassword.text.clear()
                                    binding.registerTxtPasswordConfirmation.text.clear()
                                }
                            }
                        ) {
                            override fun getParams(): MutableMap<String, String> {
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
                                if (position != null) {
                                    params["latitude"] = position?.latitude.toString()
                                    params["longitude"] = position?.longitude.toString()
                                }
                                params["status"] = "0"
                                return params
                            }
                        }
                        requestQueue.add(request)

                    },
                    { error ->
                        Log.e("Laravel", error.toString())

                        val url = Connection.URL + "users/insert"
                        val request = object: StringRequest(
                            Method.POST,
                            url,
                            object: Response.Listener<String> {
                                override fun onResponse(response: String) {
                                    Toast.makeText(this@RegisterActivity, "Berhasil mendaftar!", Toast.LENGTH_SHORT).show()
                                    Log.d("Laravel", response)
                                    finish()
                                }
                            },
                            object: Response.ErrorListener {
                                override fun onErrorResponse(error: VolleyError?) {
                                    Toast.makeText(this@RegisterActivity, "Gagal mendaftar!", Toast.LENGTH_SHORT).show()
                                    Log.e("Laravel", error.toString())
                                    binding.registerTxtPassword.text.clear()
                                    binding.registerTxtPasswordConfirmation.text.clear()
                                }
                            }
                        ) {
                            override fun getParams(): MutableMap<String, String> {
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
                                if (position != null) {
                                    params["latitude"] = position?.latitude.toString()
                                    params["longitude"] = position?.longitude.toString()
                                }
                                return params
                            }
                        }
                        requestQueue.add(request)
                    }
                )
                requestQueue.add(anotherRequest)
            }
        }

        binding.registerBtnBack.setOnClickListener {
            finish()
        }
    }
}