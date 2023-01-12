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

class ListPuskesmasAdminAdapter(
    private var users: JSONArray,
    private var context: Context
): RecyclerView.Adapter<ListPuskesmasAdminAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvEmail:TextView = view.findViewById(R.id.tvEmailListAdmin)
        val tvNama:TextView = view.findViewById(R.id.tvNamaListAdmin)
        val tvAlamat:TextView = view.findViewById(R.id.tvAlamatListAdmin)
        val tvNomorTelp:TextView = view.findViewById(R.id.tvTambahanListAdmin)
        val btnBan:Button = view.findViewById(R.id.btnBanListAdmin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_admin, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = (users[position] as JSONObject)
        holder.tvEmail.text = user.getString("email")
        holder.tvNama.text = user.getString("name")
        holder.tvAlamat.text = user.getString("address")
        holder.tvNomorTelp.text = user.getString("phone")
        if(user.getString("status") == "1") holder.btnBan.text = "Unban"
        holder.btnBan.setOnClickListener {
            var statusBanKirim = 0
            if (holder.btnBan.text != "Unban") statusBanKirim = 1
            
            val requestQueue = Volley.newRequestQueue(context)

            val url = Connection.URL + "users/ban"
            val request = object: StringRequest(
                Method.POST,
                url,
                object: Response.Listener<String> {
                    override fun onResponse(response: String) {
//                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()

                        this@ListPuskesmasAdminAdapter.updateUsers(position,statusBanKirim)
                        this@ListPuskesmasAdminAdapter.notifyDataSetChanged()
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

    fun updateUsers(index:Int, statusBaru:Int) {
        var newUsers = JSONArray()
        for (i in 0 until this.itemCount) {
            if (i == index) {
                var userSekarang = users[i] as JSONObject
                var newUser = JSONObject()
                newUser.put("email", userSekarang.get("email"))
                newUser.put("name", userSekarang.get("name"))
                newUser.put("address", userSekarang.get("address"))
                newUser.put("phone", userSekarang.get("phone"))
                newUser.put("status", statusBaru.toString())
                newUsers.put(newUser)
            }
            else {
                newUsers.put(users[i] as JSONObject)
            }
        }
        users = newUsers
    }

    override fun getItemCount(): Int {
        return users.length()
    }
}