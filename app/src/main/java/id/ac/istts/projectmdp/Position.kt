package id.ac.istts.projectmdp

import com.google.android.gms.maps.model.LatLng

data class Position(
    val position: LatLng,
    val name: String
) {
}