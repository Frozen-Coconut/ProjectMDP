package id.ac.istts.projectmdp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import id.ac.istts.projectmdp.user.Adapter.ListRequestAdapter

class AdminPuskesmasFragment : Fragment() {

    lateinit var rvPuskesmas:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_puskesmas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPuskesmas = view.findViewById(R.id.rvAdminPuskesmas)

        val requestQueue = Volley.newRequestQueue(requireContext())

        rvPuskesmas.layoutManager = LinearLayoutManager(requireContext())

        val url = Connection.URL + "users/get-puskesmas"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                rvPuskesmas.adapter = ListPuskesmasAdminAdapter(response, requireContext())
                Log.d("Laravel", response.toString())
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal mendapatkan daftar puskesmas atau rumah sakit!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)
    }
}