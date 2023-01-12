package id.ac.istts.projectmdp.user.Adapter

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

class ListRequestAdapter(
    private var requests: JSONArray,
    private var context: Context
): RecyclerView.Adapter<ListRequestAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvGolDarah: TextView = view.findViewById(R.id.tvGolonganListRequest)
        val tvDeadline: TextView = view.findViewById(R.id.tvTanggalListRequest)
        val tvPuskesmas: TextView = view.findViewById(R.id.tvPuskesmasListRequest)
        val btnAccept: Button = view.findViewById(R.id.btnAcceptListRequest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_request, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = (requests[position] as JSONObject)
        holder.tvGolDarah.text = "Golongan Darah : "+request.getString("blood_type")
        holder.tvDeadline.text = "Tanggal : "+request.getString("scheduled_date")
        holder.tvPuskesmas.text = "Nama Pemohon : "+request.getString("name")
        holder.btnAccept.setOnClickListener {
//            Toast.makeText(context, "Coba ${request.getString("id")}", Toast.LENGTH_SHORT).show()
            val requestQueue = Volley.newRequestQueue(context)

            val url = Connection.URL + "bloodrequestsusers/insert"
            val request = object: StringRequest(
                Method.POST,
                url,
                object: Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()

                        requests.remove(position)
                        this@ListRequestAdapter.notifyDataSetChanged()
                    }
                },
                object: Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(context, "Gagal menerima !", Toast.LENGTH_SHORT).show()
                        Log.e("Laravel", error.toString())
                    }
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["email"] = Connection.email
                    params["blood_request_id"] = request.getString("id")
                    return params
                }
            }
            requestQueue.add(request)
        }
    }

    override fun getItemCount(): Int {
        return requests.length()
    }
}