package com.freegym.ej.freegym

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.freegym.ej.freegym.fragment.EquipmentsFragment
import com.freegym.ej.freegym.fragment.MapsFragment
import com.freegym.ej.freegym.fragment.StretchingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appbar)

        initBottomNavigationView()
        initFragment()
        ensureGetLocationPermission()
    }

    private fun initBottomNavigationView() {
        val bottomNavigationView = bottom_navigation
        bottomNavigationView.setOnNavigationItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                R.id.ic_equipment ->
                    handleIconEquipmentTap()
                R.id.ic_stretching ->
                    handleIconStretchingTap()
                R.id.ic_map ->
                    handleIconMapTap()
            }

            true
        }
    }

    private fun initFragment() {
        handleIconEquipmentTap()
    }

    private fun handleIconEquipmentTap() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_fragment, EquipmentsFragment())
                .addToBackStack(null)
                .commit()

        appbar.title = "Equipamentos"
    }

    private fun handleIconStretchingTap() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_fragment, StretchingsFragment())
                .addToBackStack(null)
                .commit()

        appbar.title = "Alongamentos"
    }

    private fun handleIconMapTap() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ensureGetLocationPermission();
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.home_fragment, MapsFragment())
                    .addToBackStack(null)
                    .commit()
            appbar.title = "Academias"
        }
    }

    private fun ensureGetLocationPermission() {
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
