package id.ac.istts.projectmdp.hospital

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.istts.projectmdp.R
import org.json.JSONArray
import org.json.JSONObject

class ListUserAdapter(
    private var users: JSONArray
): RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.list_user_txtName)
        val txtDate: TextView = view.findViewById(R.id.list_user_txtDate)
        val txtAddress: TextView = view.findViewById(R.id.list_user_txtAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = (users[position] as JSONObject)
        holder.txtName.text = user.getString("name")
        holder.txtDate.text = user.getString("date_of_birth")
        holder.txtAddress.text = user.getString("address")
    }

    override fun getItemCount(): Int {
        return users.length()
    }
}