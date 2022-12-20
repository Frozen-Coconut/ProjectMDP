package id.ac.istts.projectmdp.user.Fragment

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.ac.istts.projectmdp.Connection
import id.ac.istts.projectmdp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserHomeFragment : Fragment() {
    private val callback = OnMapReadyCallback { googleMap ->

        val ioScope = CoroutineScope(Dispatchers.IO)

        val geocoder = Geocoder(requireContext())

        val istts = LatLng(-7.291290184677537, 112.75882726352205)
        googleMap.addMarker(MarkerOptions().position(istts).title("Institut Sains dan Teknologi Terpadu Surabaya"))

        val requestQueue = Volley.newRequestQueue(requireContext())
        val url = Connection.URL + "users/get?email=" + Connection.email
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                ioScope.launch {
                    val home = geocoder.getFromLocationName(response.getString("address"), 10)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(home[0].latitude, home[0].longitude)))
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), response.getString("address"), Toast.LENGTH_SHORT).show()
                    }
                }
                Log.d("Laravel", response.toString())
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        // ...
    }
}