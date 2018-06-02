package com.freegym.ej.freegym

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ajts.androidmads.sqliteimpex.SQLiteImporterExporter
import com.freegym.ej.freegym.model.Gym
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.maps.android.SphericalUtil

class GymMapsActivity : AppCompatActivity(), OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private lateinit var mMap: GoogleMap

    private val TAG = "MainActivity"
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    private var listGym: ArrayList<Gym> = ArrayList()
    private lateinit var nearestGymLatLng: LatLng
    private var nearestGymDistance: Double = Double.MAX_VALUE


    override fun onConnected(p0: Bundle?) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startLocationUpdates()

        var fusedLocationProviderClient:
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        mLocation = location
                        nearestGym(5)
//                        mMap.addMarker(MarkerOptions().position(LatLng(mLocation.latitude, mLocation.longitude))
//                                .title("Current Location"))

                        mMap.addMarker(MarkerOptions().position(nearestGymLatLng)
                                .title("Academia próxíma")).showInfoWindow()
//                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nearestGymLatLng, 14.5f))

                    }
                })
    }

    override fun onConnectionSuspended(p0: Int) {

        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    override fun onLocationChanged(location: Location) {
//        val currentLocation = LatLng(location.latitude, location.longitude)
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14.5f))
    }

    protected fun startLocationUpdates() {

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var sqLiteImporterExporter: SQLiteImporterExporter
        val db = "gym.db"

        sqLiteImporterExporter = SQLiteImporterExporter(this, db)
        sqLiteImporterExporter.importDataBaseFromAssets()
        var database: SQLiteDatabase = sqLiteImporterExporter.readableDatabase

        val cursor: Cursor

        cursor = database.rawQuery("SELECT * FROM Places", null);
        listGym.clear()

        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    listGym.add(Gym(cursor.getString(cursor.getColumnIndex("Nome")),
                            cursor.getString(cursor.getColumnIndex("Logradouro")),
                            cursor.getString(cursor.getColumnIndex("Bairro")),
                            cursor.getDouble(cursor.getColumnIndex("Latitude")),
                            cursor.getDouble(cursor.getColumnIndex("Longitude"))))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()

        setContentView(R.layout.activity_gym_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient.connect()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient()


//            gymDatabase!!.createDataBase()
//            gymDatabase!!.openDataBase()
//            var teste = gymDatabase!!.searchNearestPlaces(currentLocation, 5)

//            val currentLocationInt = SphericalUtil.computeDistanceBetween(home, currentLocation)
//            val currentLocationInt2 = SphericalUtil.computeDistanceBetween(home, currentLocation2)
//            val currentLocationInt3 = SphericalUtil.computeDistanceBetween(home, currentLocation3)


//            var nearest = 0.0
//            nearest = when {
//                nearest <= currentLocationInt -> currentLocationInt
//                nearest <= currentLocationInt2 -> currentLocationInt2
//                else -> currentLocationInt3
//            }

//            Toast.makeText(this, "Nearest $nearest", Toast.LENGTH_SHORT).show()

            mMap.isMyLocationEnabled = true
        } else {
            checkLocationPermission()
        }
    }

    fun nearestGym(distante: Int) {
        listGym.forEach { place ->
            var placeLocation = LatLng(place.latitude, place.longitude)
            var distante = SphericalUtil.computeDistanceBetween(placeLocation, LatLng(mLocation.latitude, mLocation.longitude))

            if (distante < nearestGymDistance){
                nearestGymDistance = distante
                nearestGymLatLng = placeLocation
            }
        }
    }

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(this,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    MY_PERMISSIONS_REQUEST_LOCATION)
                        })
                        .create()
                        .show()
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
        }
    }

}