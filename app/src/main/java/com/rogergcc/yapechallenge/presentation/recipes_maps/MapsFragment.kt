package com.rogergcc.yapechallenge.presentation.recipes_maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.rogergcc.yapechallenge.R
import com.rogergcc.yapechallenge.domain.model.Ubication


class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val ubication = Ubication()
        val latLngGeoPosition = LatLng(ubication.latitude, ubication.longitude)
        moveCameraZoomInGMap(googleMap, latLngGeoPosition)

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(),
            R.raw.custom_map))

        addMarkerToGMap(googleMap,
            latLngGeoPosition,
            R.drawable.icons8_recipe_64,
            R.string.message_ubication,
            ubication.name)
        disableOptionsGMaps(googleMap)

    }

    private fun moveCameraZoomInGMap(googleMap: GoogleMap, latLng1: LatLng) {
        val zoom = 16F
        val centerMap = LatLng(latLng1.latitude, latLng1.longitude)
        val cu: CameraUpdate = CameraUpdateFactory.newLatLngZoom(centerMap, zoom)
        googleMap.moveCamera(cu)
    }

    private fun addMarkerToGMap(
        googleMap: GoogleMap,
        mLocationLatLng: LatLng,
        mDrawableResource: Int,
        mtitle: Int,
        mSnippet: String,
    ) {
        googleMap.addMarker(MarkerOptions()
            .position(mLocationLatLng)
            .icon(BitmapDescriptorFactory.fromResource(mDrawableResource))
            .title(getString(mtitle))
            .snippet(mSnippet))
    }

    fun disableOptionsGMaps(mGoogleMap: GoogleMap) {
        mGoogleMap.isTrafficEnabled = false
        mGoogleMap.isBuildingsEnabled = false
        mGoogleMap.isIndoorEnabled = false
        mGoogleMap.uiSettings.isZoomControlsEnabled = false
        mGoogleMap.uiSettings.isRotateGesturesEnabled = false
        mGoogleMap.uiSettings.isZoomGesturesEnabled = false
        mGoogleMap.uiSettings.isTiltGesturesEnabled = false
        mGoogleMap.uiSettings.isScrollGesturesEnabled = false
    }
}