package id.ac.istts.projectmdp.hospital

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.ac.istts.projectmdp.Connection
import id.ac.istts.projectmdp.R

class PuskesmasProfileUserFragment : Fragment() {
    lateinit var tpNama: EditText
    lateinit var tpAlamat: EditText
    lateinit var tpTglLahir: EditText
    lateinit var tpEmail: EditText
    lateinit var btnEdit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tpNama = view.findViewById(R.id.tpNamaProfileUser)
        tpAlamat = view.findViewById(R.id.tpAlamatProfileUser)
        tpEmail = view.findViewById(R.id.tpEmailProfileUser)
        tpTglLahir = view.findViewById(R.id.tpTanggalLahirProfileUser)
        btnEdit = view.findViewById(R.id.btnEdit2)

        tpNama.isEnabled = false
        tpAlamat.isEnabled = false
        tpTglLahir.isEnabled = false

        val requestQueue = Volley.newRequestQueue(context)
        val url = Connection.URL + "users/get?id=${Connection.profileId}"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    tpNama.setText(response.getString("name"))
                    tpAlamat.setText(response.getString("address"))
                    tpEmail.setText(response.getString("email"))
                    tpTglLahir.setText(response.getString("date_of_birth"))
                    Log.d("Laravel", response.toString())
                } catch (ex: Exception) {
                    Toast.makeText(context, "Request Error!", Toast.LENGTH_SHORT).show()
                    Log.e("Laravel", ex.message.toString())
                }
            },
            { error ->
                Toast.makeText(context, "Gagal load profile!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)

        btnEdit.visibility = View.GONE
    }
}