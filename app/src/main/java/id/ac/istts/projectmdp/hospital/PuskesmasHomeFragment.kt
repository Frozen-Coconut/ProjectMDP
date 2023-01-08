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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.ac.istts.projectmdp.Connection
import id.ac.istts.projectmdp.R

class PuskesmasHomeFragment : Fragment() {
    lateinit var btnSubmit: Button
    lateinit var txtScheduledDate: EditText
    lateinit var txtBloodType: EditText
    lateinit var rvUser: RecyclerView
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_puskesmas_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        btnSubmit = view.findViewById(R.id.puskesmas_home_btnSubmit)
        txtScheduledDate = view.findViewById(R.id.puskesmas_home_txtScheduledDate)
        txtBloodType = view.findViewById(R.id.puskesmas_home_txtBloodType)
        rvUser = view.findViewById(R.id.rvUserHome)
        
        val requestQueue = Volley.newRequestQueue(requireContext())

        btnSubmit.setOnClickListener {
            if (txtScheduledDate.text.isEmpty() || txtBloodType.text.isEmpty()) {
                Toast.makeText(requireContext(), "Semua input harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val url = Connection.URL + "bloodrequests/insert"
                val request = object: StringRequest(
                    Method.POST,
                    url,
                    object: Response.Listener<String> {
                        override fun onResponse(response: String) {
                            Toast.makeText(requireContext(), "Berhasil membuat request darah!", Toast.LENGTH_SHORT).show()
                            Log.d("Laravel", response)
                            txtScheduledDate.text.clear()
                            txtBloodType.text.clear()
                        }
                    },
                    object: Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError?) {
                            Toast.makeText(requireContext(), "Gagal membuat request darah!", Toast.LENGTH_SHORT).show()
                            Log.e("Laravel", error.toString())
                        }
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["scheduled_date"] = txtScheduledDate.text.toString()
                        params["blood_type"] = txtBloodType.text.toString()
                        params["puskesmas_id"] = Connection.current.toString()
                        return params
                    }
                }
                requestQueue.add(request)
            }
        }

        rvUser.layoutManager = LinearLayoutManager(requireContext())

        val url = Connection.URL + "users?type=user"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                rvUser.adapter = ListUserAdapter(response)
                Log.d("Laravel", response.toString())
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal mendapatkan daftar user!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)
    }
}