package com.rogergcc.yapechallenge.presentation.recipes_maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rogergcc.yapechallenge.R


class RecipeMapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_map, container, false)
    }

//    private fun moveCameraZoomTwoPointsInGMap(latLng1: LatLng, latLng2: LatLng) {
//        val builder: LatLngBounds.Builder = Builder()
//        builder.include(latLng1)
//        builder.include(latLng2)
//        val bounds: LatLngBounds = builder.build()
//        val cu: CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200)
//        googleMap.moveCamera(cu)
//    }
//
//    fun disableOptionsGMaps(mGoogleMap: GoogleMap) {
//        mGoogleMap.setTrafficEnabled(false)
//        mGoogleMap.setBuildingsEnabled(false)
//        mGoogleMap.setIndoorEnabled(false)
//        mGoogleMap.getUiSettings().setZoomControlsEnabled(false)
//        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false)
//        mGoogleMap.getUiSettings().setZoomGesturesEnabled(false)
//        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false)
//        mGoogleMap.getUiSettings().setScrollGesturesEnabled(false)
//        mGoogleMap.setMyLocationEnabled(false)
//    }
//    private fun addMarkerToGMap(
//        mLocationLatLng: LatLng,
//        mDrawableResource: Int,
//        mtitle: Int,
//        mSnippet: String,
//    ) {
//        googleMap.addMarker(MarkerOptions()
//            .position(mLocationLatLng)
//            .icon(BitmapDescriptorFactory.fromResource(mDrawableResource))
//            .title(getString(mtitle))
//            .snippet(mSnippet))
//    }

    companion object
}