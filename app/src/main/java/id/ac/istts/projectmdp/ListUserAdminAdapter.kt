package id.ac.istts.projectmdp

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
import org.json.JSONArray
import org.json.JSONObject

class ListUserAdminAdapter(
    private var users: JSONArray,
    private var context: Context
): RecyclerView.Adapter<ListUserAdminAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvEmail: TextView = view.findViewById(R.id.tvEmailListAdmin)
        val tvNama: TextView = view.findViewById(R.id.tvNamaListAdmin)
        val tvAlamat: TextView = view.findViewById(R.id.tvAlamatListAdmin)
        val tvTanggalLahir: TextView = view.findViewById(R.id.tvTambahanListAdmin)
        val btnBan: Button = view.findViewById(R.id.btnBanListAdmin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_admin, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = (users[position] as JSONObject)
        holder.tvEmail.text = user.getString("email")
        holder.tvNama.text = user.getString("name")
        holder.tvAlamat.text = user.getString("address")
        holder.tvTanggalLahir.text = user.getString("date_of_birth")
        if(user.getString("status") == "1") holder.btnBan.text = "Unban"
        holder.btnBan.setOnClickListener {
            var statusBanKirim = 0
            if (holder.btnBan.text == "Ban") statusBanKirim = 1

            Toast.makeText(context, statusBanKirim.toString(), Toast.LENGTH_SHORT).show()

            val requestQueue = Volley.newRequestQueue(context)

            val url = Connection.URL + "users/ban"
            val request = object: StringRequest(
                Method.POST,
                url,
                object: Response.Listener<String> {
                    override fun onResponse(response: String) {
//                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
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
                    params["email"] = user.getString("email")
                    params["status"] = statusBanKirim.toString()
                    return params
                }
            }
            requestQueue.add(request)
        }
    }

    override fun getItemCount(): Int {
        return users.length()
    }
}