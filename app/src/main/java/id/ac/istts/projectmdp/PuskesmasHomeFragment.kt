package id.ac.istts.projectmdp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.ac.istts.projectmdp.databinding.FragmentPuskesmasHomeBinding

class PuskesmasHomeFragment : Fragment() {
    private lateinit var binding: FragmentPuskesmasHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_puskesmas_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPuskesmasHomeBinding.inflate(layoutInflater)
        val fragmentView = binding.root
        requireActivity().setContentView(fragmentView)

        binding.puskesmasHomeBtnSubmit.setOnClickListener {
            if (binding.puskesmasHomeTxtScheduledDate.text.isEmpty() || binding.puskesmasHomeTxtBloodType.text.isEmpty()) {
                Toast.makeText(requireContext(), "Semua input harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val requestQueue = Volley.newRequestQueue(requireContext())
                val url = Connection.URL + "bloodrequests/insert"
                val request = object: StringRequest(
                    Method.POST,
                    url,
                    object: Response.Listener<String> {
                        override fun onResponse(response: String) {
                            Toast.makeText(requireContext(), "Berhasil membuat request darah!", Toast.LENGTH_SHORT).show()
                            Log.d("Laravel", response)
                            binding.puskesmasHomeTxtScheduledDate.text.clear()
                            binding.puskesmasHomeTxtBloodType.text.clear()
                        }
                    },
                    object: Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError?) {
                            Toast.makeText(requireContext(), "Gagal membuat request darah!", Toast.LENGTH_SHORT).show()
                            Log.e("Laravel", error.toString())
                        }
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["scheduled_date"] = binding.puskesmasHomeTxtScheduledDate.text.toString()
                        params["blood_type"] = binding.puskesmasHomeTxtBloodType.text.toString()
                        params["puskesmas_id"] = Connection.current.toString()
                        return params
                    }
                }
                requestQueue.add(request)
            }
        }
    }
}