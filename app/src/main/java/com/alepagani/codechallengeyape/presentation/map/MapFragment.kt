package com.alepagani.codechallengeyape.presentation.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.alepagani.codechallengeyape.BuildConfig
import com.alepagani.codechallengeyape.R
import com.alepagani.codechallengeyape.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var binding: FragmentMapBinding
    private val args by navArgs<MapFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        Places.initialize(requireContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(requireContext())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setMarkerAtLocation()
    }

    private fun setMarkerAtLocation() {
        val latLng = LatLng(args.latitude.toDouble(), args.longitude.toDouble())
        val markerOptions = MarkerOptions().position(latLng).title(args.name)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
    }
}
