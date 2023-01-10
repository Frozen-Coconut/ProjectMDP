package id.ac.istts.projectmdp.user.Fragment

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
import id.ac.istts.projectmdp.user.Adapter.ListNotificationAdapter
import id.ac.istts.projectmdp.user.Adapter.ListRequestAdapter

class UserNotificationFragment : Fragment() {

    lateinit var rvNotification: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvNotification = view.findViewById(R.id.rvUserNotification)

        updateRecyclerView()
    }

    fun updateRecyclerView(): Unit {

        val requestQueue = Volley.newRequestQueue(requireContext())
        rvNotification.layoutManager = LinearLayoutManager(requireContext())

        val url = Connection.URL + "notifications/getAll?email=" + Connection.email
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                rvNotification.adapter = ListNotificationAdapter(response, requireContext())
                Log.d("Laravel", response.toString())
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal mendapatkan notifikasi!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)
    }
}