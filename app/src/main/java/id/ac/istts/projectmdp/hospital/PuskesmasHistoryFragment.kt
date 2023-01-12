package id.ac.istts.projectmdp.hospital

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
import id.ac.istts.projectmdp.Connection
import id.ac.istts.projectmdp.R
import id.ac.istts.projectmdp.user.Adapter.ListHistoryAdapter

class PuskesmasHistoryFragment : Fragment() {
    lateinit var RvHistory: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_puskesmas_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RvHistory = view.findViewById(R.id.RvPuskesmasHistory)

        val requestQueue = Volley.newRequestQueue(requireContext())

        RvHistory.layoutManager = LinearLayoutManager(requireContext())

        val url = Connection.URL + "bloodrequestsusers/history?email=${Connection.email}"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                RvHistory.adapter = ListPuskesmasHistoryAdapter(response, requireContext())
                Log.d("Laravel", response.toString())
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal meminta history", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)
    }
}