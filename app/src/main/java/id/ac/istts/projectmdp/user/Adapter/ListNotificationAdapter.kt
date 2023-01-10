package id.ac.istts.projectmdp.user.Adapter

import android.annotation.SuppressLint
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

class ListNotificationAdapter(
    private var notifications: JSONArray,
    private var context: Context
): RecyclerView.Adapter<ListNotificationAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvText: TextView = view.findViewById(R.id.tvListNotification)
        val btnDelete: Button = view.findViewById(R.id.btnDeleteListNotification)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_notification, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val notification = (notifications[position] as JSONObject)
        holder.tvText.text = notification.getString("text")
        holder.btnDelete.setOnClickListener {
//            Toast.makeText(context, "Coba ${request.getString("id")}", Toast.LENGTH_SHORT).show()
            val requestQueue = Volley.newRequestQueue(context)

            val url = Connection.URL + "notifications/delete"
            val request = object: StringRequest(
                Method.POST,
                url,
                object: Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                        notifications.remove(position)
                        this@ListNotificationAdapter.notifyDataSetChanged()
                    }
                },
                object: Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(context, "Gagal delete !", Toast.LENGTH_SHORT).show()
                        Log.e("Laravel", error.toString())
                    }
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["id"] = notification.getString("id")
                    return params
                }
            }
            requestQueue.add(request)
        }
    }

    override fun getItemCount(): Int {
        return notifications.length()
    }
}