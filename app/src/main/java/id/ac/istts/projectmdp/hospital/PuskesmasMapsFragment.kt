package id.ac.istts.projectmdp.hospital

import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.ac.istts.projectmdp.Connection
import id.ac.istts.projectmdp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import java.net.URLEncoder

class PuskesmasMapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
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
                Log.d("Laravel", response.toString())
                val anotherUrl = "https://geocode.search.hereapi.com/v1/geocode?q=" + URLEncoder.encode(response.getString("address"), "UTF-8") + "&apiKey=whXA9dgTB1kCYoeqesmMxMGhFTZrmz3FNK70aHbRF88"
                val anotherRequest = JsonObjectRequest(
                    Request.Method.GET,
                    anotherUrl,
                    null,
                    { anotherResponse ->
                        val home = (anotherResponse.getJSONArray("items")[0] as JSONObject).getJSONObject("position")
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(home.getDouble("lat"), home.getDouble("lng"))))
                    },
                    { error ->
                        Toast.makeText(requireContext(), "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                        Log.e("Laravel", error.toString())
                    }
                )
                requestQueue.add(anotherRequest)
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_puskesmas_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}