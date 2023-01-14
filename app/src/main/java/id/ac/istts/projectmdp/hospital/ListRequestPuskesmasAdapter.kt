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

class ListRequestPuskesmasAdapter(
    private var requests: JSONArray,
    private var context: Context
): RecyclerView.Adapter<ListRequestPuskesmasAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtUserData: TextView = view.findViewById(R.id.txtUserData)
        val txtRequestData: TextView = view.findViewById(R.id.txtRequestData)
        val btnAccept: Button = view.findViewById(R.id.btnAccept)
        val btnReject: Button = view.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_puskesmas_list_request, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = position
        val request = (requests[position] as JSONObject)
        holder.txtUserData.text = "${request.getString("user_name")} (${request.getString("user_email")})"
        holder.txtRequestData.text = "Blood Type: ${request.getString("blood_type")}\nScheduled Date: ${request.getString("scheduled_date")}"
        holder.btnAccept.setOnClickListener {
            val requestQueue = Volley.newRequestQueue(context)
            var url = Connection.URL + "bloodrequestsusers/update"
            val updateRequest = object: StringRequest(
                Method.POST,
                url,
                object: Response.Listener<String> {
                    override fun onResponse(response: String) {
                        requests.remove(pos)
                        this@ListRequestPuskesmasAdapter.notifyDataSetChanged()
                        Log.d("Laravel", response)
                    }
                },
                object: Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(context, "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                        Log.e("Laravel", error.toString())
                    }
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["id"] = request.getString("id")
                    params["status"] = "1"
                    return params
                }
            }
            requestQueue.add(updateRequest)

            val urlInsert = Connection.URL + "notifications/insert"
            val insertNotification = object: StringRequest(
                Method.POST,
                urlInsert,
                object: Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Log.d("Laravel", response)
                    }
                },
                object: Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(context, "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                        Log.e("Laravel", error.toString())
                    }
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["email"] = request.getString("user_email")
                    params["text"] = "Pengajuan darah dengan golongan darah ${request.getString("blood_type")} pada tanggal ${request.getString("scheduled_date")} diterima"
                    params["status"] = "0"
                    return params
                }
            }
            requestQueue.add(insertNotification)
        }
        holder.btnReject.setOnClickListener {
            val requestQueue = Volley.newRequestQueue(context)
            var url = Connection.URL + "bloodrequestsusers/update"
            val updateRequest = object: StringRequest(
                Method.POST,
                url,
                object: Response.Listener<String> {
                    override fun onResponse(response: String) {
                        requests.remove(pos)
                        this@ListRequestPuskesmasAdapter.notifyDataSetChanged()
                        Log.d("Laravel", response)
                    }
                },
                object: Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(context, "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                        Log.e("Laravel", error.toString())
                    }
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["id"] = request.getString("id")
                    params["status"] = "2"
                    return params
                }
            }
            requestQueue.add(updateRequest)

            val urlInsert = Connection.URL + "notifications/insert"
            val insertNotification = object: StringRequest(
                Method.POST,
                urlInsert,
                object: Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Log.d("Laravel", response)
                    }
                },
                object: Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(context, "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                        Log.e("Laravel", error.toString())
                    }
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["email"] = request.getString("user_email")
                    params["text"] = "Pengajuan darah dengan golongan darah ${request.getString("blood_type")} pada tanggal ${request.getString("scheduled_date")} ditolak"
                    params["status"] = "0"
                    return params
                }
            }
            requestQueue.add(insertNotification)
        }
    }

    override fun getItemCount(): Int {
        return requests.length()
    }
}