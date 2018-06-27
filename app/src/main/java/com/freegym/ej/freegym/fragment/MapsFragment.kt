package com.freegym.ej.freegym.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ajts.androidmads.sqliteimpex.SQLiteImporterExporter
import com.freegym.ej.freegym.R
import com.freegym.ej.freegym.model.GymModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil


class MapsFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private val TAG = "MainActivity"
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    private var listGymModel: ArrayList<GymModel> = ArrayList()
    private var listNearGymModel: ArrayList<GymModel> = ArrayList()
    private lateinit var nearestGymLatLng: LatLng
    private var nearestGymDistance: Double = Double.MAX_VALUE
    private lateinit var nearestPlace: GymModel
    private val DISTANCE: Int = 3000 //5km


    private lateinit var mMapView: MapView
    private lateinit var mMap: GoogleMap


    companion object {
        fun newInstance(): MapsFragment {
            return MapsFragment()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.loadGyms()

        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        mMapView = rootView.findViewById(R.id.mapView)
        this.mMapView.onCreate(savedInstanceState)

        mMapView.onResume()

        try {
            MapsInitializer.initialize(this.activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.getMapAsync({ mMap ->
            this.mMap = mMap

            // For showing a move to my location button
            this.mMap.isMyLocationEnabled = true

            startLocationUpdates()

            var fusedLocationProviderClient:
                    FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.activity!!);
            fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener({ location ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            mLocation = location
                            nearestGym()

                            listNearGymModel.forEach { nearGym ->
                                mMap.addMarker(MarkerOptions().position(LatLng(nearGym.latitude, nearGym.longitude))
                                        .title(nearGym.name)).showInfoWindow()
                            }

                            mMap.addMarker(MarkerOptions().position(nearestGymLatLng)
                                    .title("Academia mais próxíma")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).showInfoWindow()

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nearestGymLatLng, 14.5f))

                        }
                    })
        })

        return rootView
    }

    private fun loadGyms() {
        var sqLiteImporterExporter: SQLiteImporterExporter
        val db = "gym.db"

        sqLiteImporterExporter = SQLiteImporterExporter(this.activity, db)
        sqLiteImporterExporter.importDataBaseFromAssets()
        var database: SQLiteDatabase = sqLiteImporterExporter.readableDatabase

        val cursor: Cursor

        cursor = database.rawQuery("SELECT * FROM Places", null);
        listGymModel.clear()

        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    listGymModel.add(GymModel(cursor.getString(cursor.getColumnIndex("Nome")),
                            cursor.getString(cursor.getColumnIndex("Logradouro")),
                            cursor.getString(cursor.getColumnIndex("Bairro")),
                            cursor.getDouble(cursor.getColumnIndex("Latitude")),
                            cursor.getDouble(cursor.getColumnIndex("Longitude"))))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this.activity!!)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient.connect()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.mMap = googleMap

        if (ContextCompat.checkSelfPermission(this.activity!!,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient()

            this.mMap.isMyLocationEnabled = true
        } else {
            checkLocationPermission()
        }
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
    }

    override fun onLocationChanged(location: Location) {
    }

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity!!,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(this.activity!!)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(this.activity!!,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    MY_PERMISSIONS_REQUEST_LOCATION)
                        })
                        .create()
                        .show()
            } else {
                ActivityCompat.requestPermissions(this.activity!!,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
        }
    }

    private fun startLocationUpdates() {

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this.activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        LocationServices.getFusedLocationProviderClient(this.activity!!)
    }

    fun nearestGym() {
        listGymModel.forEach { place ->
            var placeLocation = LatLng(place.latitude, place.longitude)
            var distantePlace = SphericalUtil.computeDistanceBetween(placeLocation, LatLng(mLocation.latitude, mLocation.longitude))

            if (distantePlace < nearestGymDistance) {
                nearestGymDistance = distantePlace
                nearestGymLatLng = placeLocation
                nearestPlace = place
            }

            if (distantePlace <= (DISTANCE)) {
                listNearGymModel.add(place)
            }

        }

        listNearGymModel.remove(nearestPlace)
    }

}
