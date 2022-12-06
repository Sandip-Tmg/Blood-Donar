package com.example.blood_donar

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.blood_donar.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class ShowLocation : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var currentLatitude: String
    private lateinit var currentLongitude: String
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var currentLocation: Location

    //retrieve last known location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocationUser()

    }

    @SuppressLint("Range")
    private fun getCurrentLocationUser() {
        //here we check permission is granted or not
        //if the permission is not granted, permission is requested
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                permissionCode
            )
            return
        }
        //addOnSuccessListener is used when task completes successfully
        val getLocation =
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                //check if location is null or not because there might be a situation where location is turned off in setting
                if (location != null) {
                    currentLocation = location
                    currentLatitude = currentLocation.latitude.toString()
                    currentLongitude = currentLocation.longitude.toString()

                    //this function notify when the map is ready to be used.
                    val mapFragment = supportFragmentManager
                        .findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)

                }
            }
    }

    //this will request for location permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            //location permission granted
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocationUser()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //latLng combination of latitude and longitude coordinate and store as degrees
        val latLng = LatLng(
            currentLocation.latitude,
            currentLocation.longitude
        )

        val currentLocationMarker = MarkerOptions().position(latLng).title("Current Location").icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                10.0f
            )
        )
        googleMap?.addMarker(currentLocationMarker)

        // intent to get the bloodType from user selection
        var blood: String? = intent.getStringExtra("Blood")

        var databaseHelper = DBHelper(applicationContext)
        var details = databaseHelper.getDetailsWithBloodType(blood)

        var locationArrayList: ArrayList<LatLng> = ArrayList()
        var allDetailsOfBloodType: ArrayList<DonarModel> = ArrayList()
        for(det in details){
            locationArrayList!!.add(LatLng(det.latitude.toDouble(), det.longitude.toDouble()))
            allDetailsOfBloodType.add(det)
        }

        if(locationArrayList.size==0){
            // message display there is no data of user selection of blood and range
            Toast.makeText(applicationContext, "Sorry couldn't find the donar with in your range", Toast.LENGTH_LONG).show()
        }else{
            for(i in locationArrayList!!.indices){
                googleMap.addMarker(MarkerOptions().position(locationArrayList!![i]).title(allDetailsOfBloodType!![i].name +" "+ allDetailsOfBloodType!![i].email))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!![i]))
                googleMap?.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        latLng,
                        10.0f
                    )
                )
            }
        }



    }

}