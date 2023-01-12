package id.ac.istts.projectmdp.user.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.ac.istts.projectmdp.Connection
import id.ac.istts.projectmdp.Position
import id.ac.istts.projectmdp.R
import id.ac.istts.projectmdp.hospital.ListUserAdapter
import id.ac.istts.projectmdp.user.Adapter.ListRequestAdapter
import id.ac.istts.projectmdp.user.UserActivity
import id.ac.istts.projectmdp.user.UserProfilePuskesmasActivity
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap
import kotlin.math.abs

class UserHomeFragment : Fragment() {
    lateinit var rvRequest: RecyclerView

    private var clickedId = -1

    private val callback = OnMapReadyCallback { googleMap ->

        val istts = LatLng(-7.291290184677537, 112.75882726352205)
//        googleMap.addMarker(MarkerOptions().position(istts).title("Institut Sains dan Teknologi Terpadu Surabaya"))

        var currentPosition = istts
        val requestQueue = Volley.newRequestQueue(requireContext())
        val url = Connection.URL + "users/get?email=" + Connection.email
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                Log.d("Laravel", response.toString())
                if (response.isNull("latitude") || response.isNull("longitude")) {
                    val anotherUrl = "https://geocode.search.hereapi.com/v1/geocode?q=" + URLEncoder.encode(response.getString("address"), "UTF-8") + "&apiKey=whXA9dgTB1kCYoeqesmMxMGhFTZrmz3FNK70aHbRF88"
                    val anotherRequest = JsonObjectRequest(
                        Request.Method.GET,
                        anotherUrl,
                        null,
                        { anotherResponse ->
                            Log.d("Laravel", anotherResponse.toString())
                            val home = (anotherResponse.getJSONArray("items")[0] as JSONObject).getJSONObject("position")
                            val position = LatLng(home.getDouble("lat"), home.getDouble("lng"))
                            currentPosition = position
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
                            googleMap.addMarker(MarkerOptions().position(position).title(response.getString("name")))
                            val updateUrl = Connection.URL + "users/update"
                            val updateRequest = object: StringRequest(
                                Method.POST,
                                updateUrl,
                                object: Response.Listener<String> {
                                    override fun onResponse(response: String) {
                                        Log.d("Laravel", response)
                                    }
                                },
                                object: Response.ErrorListener {
                                    override fun onErrorResponse(error: VolleyError?) {
                                        Toast.makeText(requireContext(), "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                                        Log.e("Laravel", error.toString())
                                    }
                                }
                            ) {
                                override fun getParams(): MutableMap<String, String> {
                                    val params: MutableMap<String, String> = HashMap()
                                    params["email"] = Connection.email
                                    params["latitude"] = home.getDouble("lat").toString()
                                    params["longitude"] = home.getDouble("lng").toString()
                                    return params
                                }
                            }
                            requestQueue.add(updateRequest)
                        },
                        { error ->
                            Toast.makeText(requireContext(), "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                            Log.e("Laravel", error.toString())
                        }
                    )
                    requestQueue.add(anotherRequest)
                } else {
                    val position = LatLng(response.getDouble("latitude"), response.getDouble("longitude"))
                    currentPosition = position
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
                    googleMap.addMarker(MarkerOptions().position(position).title(response.getString("name")))
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)

        val puskesmasRequest = JsonArrayRequest(
            Request.Method.GET,
            Connection.URL + "users?type=puskesmas",
            null,
            { response->
                if (currentPosition == istts) {
                    Toast.makeText(requireContext(), "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                } else {
                    val comparator = Comparator { position1: Position, position2: Position ->
                        if (
                            abs(position1.position.latitude - currentPosition.latitude) + abs(position1.position.longitude - currentPosition.longitude)
                            >
                            abs(position2.position.latitude - currentPosition.latitude) + abs(position2.position.longitude - currentPosition.longitude)
                        ) {
                            return@Comparator 1
                        }
                        else if (
                            abs(position1.position.latitude - currentPosition.latitude) + abs(position1.position.longitude - currentPosition.longitude)
                            ==
                            abs(position2.position.latitude - currentPosition.latitude) + abs(position2.position.longitude - currentPosition.longitude)
                        ) {
                            return@Comparator 0
                        }
                        return@Comparator -1
                    }
                    val queue = PriorityQueue(comparator)
                    for (i in 0 until response.length()) {
                        val user = response[i] as JSONObject
                        if (!user.isNull("latitude") && !user.isNull("longitude")) {
                            val position = LatLng(user.getDouble("latitude"), user.getDouble("longitude"))
                            queue.add(Position(user.getInt("id"), position, user.getString("name")))
                        }
                    }
                    for (i in 0 until 5) {
                        if (queue.size > 0) {
                            val position = queue.poll()
                            if (position != null) {
                                val marker = googleMap.addMarker(MarkerOptions().position(position.position).title(position.name))
                                marker?.tag = position.id
                            }
                        } else {
                            break
                        }
                    }
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal terhubung ke database!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(puskesmasRequest)

        googleMap.setOnMarkerClickListener {
            val id = (it.tag ?: return@setOnMarkerClickListener false) as Int
            if (clickedId != id) {
                clickedId = id
                return@setOnMarkerClickListener false
            }
            Connection.profileId = id
            startActivity(Intent(requireContext(), UserProfilePuskesmasActivity::class.java))
            clickedId = -1
            return@setOnMarkerClickListener true
        }
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

        rvRequest = view.findViewById(R.id.rvUserHome)

        val requestQueue = Volley.newRequestQueue(requireContext())

        rvRequest.layoutManager = LinearLayoutManager(requireContext())

        val url = Connection.URL + "bloodrequests/getBeforeDeadline?email=${Connection.email}"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                rvRequest.adapter = ListRequestAdapter(response, requireContext())
                Log.d("Laravel", response.toString())
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal mendapatkan daftar permintaan darah!", Toast.LENGTH_SHORT).show()
                Log.e("Laravel", error.toString())
            }
        )
        requestQueue.add(request)
    }
}