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
    private var historys: JSONArray,
    private var context: Context
): RecyclerView.Adapter<ListPuskesmasHistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvGolDarah:TextView = view.findViewById(R.id.tvGolonganDarahHistoryPuskesmas)
        val tvTanggal:TextView = view.findViewById(R.id.tvTanggalHistoryPuskesmas)
        val tvStatus:TextView = view.findViewById(R.id.tvStatusHistoryPuskesmas)
        val tvNama:TextView = view.findViewById(R.id.tvNamaHistoryPuskesmas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_history_puskesmas, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = position
        val history = (historys[position] as JSONObject)

        holder.tvGolDarah.text = "Golongan Darah : "+history.getString("blood_type")
        holder.tvTanggal.text = "Tanggal : "+history.getString("scheduled_date")
        holder.tvNama.text = "Nama Pendonor : "+history.getString("name")

        var status = "Menunggu"
        if (history.getString("status") == "1") status = "Diterima"
        if (history.getString("status") == "2") status = "Ditolak"

        holder.tvStatus.text = "Status : $status"
    }

    override fun getItemCount(): Int {
        return historys.length()
    }
}