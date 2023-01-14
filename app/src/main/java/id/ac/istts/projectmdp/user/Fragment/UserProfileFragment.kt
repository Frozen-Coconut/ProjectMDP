package id.ac.istts.projectmdp.user.Fragment

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

class UserProfileFragment : Fragment() {

    lateinit var tpNama: EditText
    lateinit var tpAlamat: EditText
    lateinit var tpTglLahir: EditText
    lateinit var tpEmail: EditText
    lateinit var btnEdit: Button

    var edit = false

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


        val requestQueue = Volley.newRequestQueue(context)
        val url = Connection.URL + "users/get?email=${Connection.email}"
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

        btnEdit.setOnClickListener {
            if (edit) {
                edit = false
                val updateUrl = Connection.URL + "users/update?email=${Connection.email}"
                val updateRequest = object: StringRequest(
                    Method.POST,
                    updateUrl,
                    { response ->
                        try {
                            Log.d("Laravel", response.toString())
                        } catch (ex: Exception) {
                            Toast.makeText(context, "Request Error!", Toast.LENGTH_SHORT).show()
                            Log.e("Laravel", ex.message.toString())
                        }
                    },
                    { error ->
                        Toast.makeText(context, "Gagal update!", Toast.LENGTH_SHORT).show()
                        Log.e("Laravel", error.toString())
                    }
                ){
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["name"] = tpNama.text.toString()
                        params["address"] = tpAlamat.text.toString()
                        params["date_of_birth"] = tpTglLahir.text.toString()
                        return params
                    }
                }
                requestQueue.add(updateRequest)

                setOnEdit()
            }
            else {
                edit = true

                setOnEdit()
            }
        }
    }

    fun setOnEdit() {
        if (edit) {
            btnEdit.text = "Save"
            tpNama.isEnabled = true
            tpAlamat.isEnabled = true
            tpTglLahir.isEnabled = true
            tpEmail.isEnabled = true
        }
        else {
            btnEdit.text = "Edit"
            tpNama.isEnabled = false
            tpAlamat.isEnabled = false
            tpTglLahir.isEnabled = false
            tpEmail.isEnabled = false
        }
    }
}