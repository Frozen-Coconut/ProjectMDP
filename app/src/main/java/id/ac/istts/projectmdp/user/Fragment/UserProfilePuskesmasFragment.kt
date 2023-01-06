package id.ac.istts.projectmdp.user.Fragment

import android.app.Activity
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

class UserProfilePuskesmasFragment : Fragment() {

    lateinit var tpNama: EditText
    lateinit var tpAlamat: EditText
    lateinit var tpNoHp: EditText
    lateinit var tpEmail: EditText
    lateinit var btnEdit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_puskesmas_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tpNama = view.findViewById(R.id.profile_txtNama)
        tpAlamat = view.findViewById(R.id.profile_txtAlamat)
        tpNoHp = view.findViewById(R.id.profile_txtNoHP)
        tpEmail = view.findViewById(R.id.profile_txtEmail)

        val requestQueue = Volley.newRequestQueue(context)
        val url = Connection.URL + "users/get?id=${Connection.profileId}"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    Toast.makeText(requireContext(), Connection.profileId.toString(), Toast.LENGTH_SHORT).show()
                    tpNama.setText(response.getString("name"))
                    tpAlamat.setText(response.getString("address"))
                    tpNoHp.setText(response.getString("phone"))
                    tpEmail.setText(response.getString("email"))
                    Log.d("Laravel", response.toString())
                } catch (ex: Exception) {
                    Toast.makeText(context, "Request Error!", Toast.LENGTH_SHORT).show()
                    Log.e("Laravel", ex.message.toString())
                }
            },
            { error ->
                Toast.makeText(context, "Gagal login!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)

        btnEdit = view.findViewById(R.id.btnEdit)
        btnEdit.visibility = View.GONE
    }
}