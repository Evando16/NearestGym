package com.freegym.ej.freegym

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.location.Location
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.ajts.androidmads.sqliteimpex.SQLiteImporterExporter
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
import java.io.IOException
import java.sql.SQLException


class GymMapsActivity : AppCompatActivity(), OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private lateinit var mMap: GoogleMap

    private val TAG = "MainActivity"
    private lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */


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
                        val currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14.5f))
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
        var path: String = Environment.getExternalStorageDirectory().absolutePath + "/"
        val db = "gym.db"

        sqLiteImporterExporter = SQLiteImporterExporter(this, db)
        sqLiteImporterExporter.importDataBaseFromAssets()
        var database: SQLiteDatabase = sqLiteImporterExporter.readableDatabase

        val cursor: Cursor

        val list = ArrayList<String>()

        cursor = database.rawQuery("SELECT * FROM Places", null);
        list.clear()

        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(cursor.getColumnIndex("Nome")))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()

//        databaseHelper = DatabaseHelper(this, "/data/data/${this.getPackageName()}/databases/", "gym.db")
//        try {
//            databaseHelper!!.createDataBase()
//        } catch (e: IOException) {
//            throw Error("Não foi possivel criar o banco")
//        }
//
//        try {
//            databaseHelper!!.openDataBase()
//        } catch (sqle: SQLException) {
//            throw sqle
//        }
//
//        var c = databaseHelper!!.query("Places", null, null, null, null, null, null)
//
//        if (c.moveToFirst()) {
//            do {
//                Toast.makeText(this, c.getString(0), Toast.LENGTH_SHORT).show()
//            }while (c.moveToNext())
//        }

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

            val home = LatLng(-19.923962, -43.911767)

            val currentLocation = LatLng(-19.923842, -43.912110)
            val currentLocation2 = LatLng(-19.930699, -43.899450)
            val currentLocation3 = LatLng(-19.929649, -43.926015)

//            gymDatabase!!.createDataBase()
//            gymDatabase!!.openDataBase()
//            var teste = gymDatabase!!.searchNearestPlaces(currentLocation, 5)

            mMap.addMarker(MarkerOptions().position(currentLocation)
                    .title("Home"))

            val currentLocationInt = SphericalUtil.computeDistanceBetween(home, currentLocation)
            val currentLocationInt2 = SphericalUtil.computeDistanceBetween(home, currentLocation2)
            val currentLocationInt3 = SphericalUtil.computeDistanceBetween(home, currentLocation3)


            var nearest = 0.0
            nearest = when {
                nearest <= currentLocationInt -> currentLocationInt
                nearest <= currentLocationInt2 -> currentLocationInt2
                else -> currentLocationInt3
            }

            Toast.makeText(this, "Nearest $nearest", Toast.LENGTH_SHORT).show()


            mMap.setMyLocationEnabled(true)
        } else {
            checkLocationPermission()
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