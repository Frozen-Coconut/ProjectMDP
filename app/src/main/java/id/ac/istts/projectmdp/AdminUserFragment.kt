package id.ac.istts.projectmdp

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
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import id.ac.istts.projectmdp.user.Adapter.ListRequestAdapter

class AdminUserFragment : Fragment() {

    lateinit var rvUser: RecyclerView
    lateinit var tpSearch: EditText
    lateinit var btnSearch: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvUser = view.findViewById(R.id.rvAdminUser)
        tpSearch = view.findViewById(R.id.tpSearchAdminUser)
        btnSearch = view.findViewById(R.id.btnSearchAdminUser)

        search()

        btnSearch.setOnClickListener {
            search()
        }
    }

    fun search() {
        val requestQueue = Volley.newRequestQueue(requireContext())

        rvUser.layoutManager = LinearLayoutManager(requireContext())

        val url = Connection.URL + "users/get-users?name=${tpSearch.text}"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                rvUser.adapter = ListUserAdminAdapter(response, requireContext())
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