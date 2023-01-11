package id.ac.istts.projectmdp.hospital

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.ac.istts.projectmdp.Connection
import id.ac.istts.projectmdp.R
import org.json.JSONArray
import org.json.JSONObject

class ListPuskesmasHistoryAdapter(
    private var requests: JSONArray,
    private var context: Context
): RecyclerView.Adapter<ListPuskesmasHistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtUserData: TextView = view.findViewById(R.id.txtUserData)
        val txtRequestData: TextView = view.findViewById(R.id.txtRequestData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_puskesmas_list_request, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = position
        val request = (requests[position] as JSONObject)
        holder.txtUserData.text = "${request.getString("user_name")} (${request.getString("user_email")})"
        holder.txtRequestData.text = "Blood Type: ${request.getString("blood_type")}\nScheduled Date: ${request.getString("scheduled_date")}"

    }

    override fun getItemCount(): Int {
        return requests.length()
    }
}