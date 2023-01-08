package id.ac.istts.projectmdp.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import id.ac.istts.projectmdp.Connection
import id.ac.istts.projectmdp.R

class UserProfilePuskesmasActivity : AppCompatActivity() {

    lateinit var tpNama: EditText
    lateinit var tpAlamat: EditText
    lateinit var tpNoHp: EditText
    lateinit var tpEmail: EditText
    lateinit var btnEdit: Button
    lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile_puskesmas)

        tpNama = findViewById(R.id.profile_txtNama)
        tpAlamat = findViewById(R.id.profile_txtAlamat)
        tpNoHp = findViewById(R.id.profile_txtNoHP)
        tpEmail = findViewById(R.id.profile_txtEmail)
        btnBack = findViewById(R.id.button3)

        val requestQueue = Volley.newRequestQueue(this)
        val url = Connection.URL + "users/get?id=${Connection.profileId}"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    Toast.makeText(this, Connection.profileId.toString(), Toast.LENGTH_SHORT).show()
                    tpNama.setText(response.getString("name"))
                    tpAlamat.setText(response.getString("address"))
                    tpNoHp.setText(response.getString("phone"))
                    tpEmail.setText(response.getString("email"))
                    Log.d("Laravel", response.toString())
                } catch (ex: Exception) {
                    Toast.makeText(this, "Request Error!", Toast.LENGTH_SHORT).show()
                    Log.e("Laravel", ex.message.toString())
                }
            },
            { error ->
                Toast.makeText(this, "Gagal login!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)

        btnEdit = findViewById(R.id.btnEdit)
        btnEdit.visibility = View.GONE

        btnBack.setOnClickListener {
            finish()
        }
    }
}