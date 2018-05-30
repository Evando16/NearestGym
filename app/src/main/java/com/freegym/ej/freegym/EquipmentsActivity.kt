package com.freegym.ej.freegym

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.freegym.ej.freegym.adapter.EquipmentsAdapter
import com.freegym.ej.freegym.model.Equipment
import kotlinx.android.synthetic.main.activity_main.*

class EquipmentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = equipments_recycler_view
        recyclerView.adapter = EquipmentsAdapter(buildEquipments(), this)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
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

    private fun buildEquipments(): List<Equipment> {
        return listOf(
                Equipment("Equipamento #1", ""),
                Equipment("Equipamento #2", ""),
                Equipment("Equipamento #3", ""),
                Equipment("Equipamento #4", ""),
                Equipment("Equipamento #5", ""),
                Equipment("Equipamento #6", ""),
                Equipment("Equipamento #7", ""),
                Equipment("Equipamento #8", ""),
                Equipment("Equipamento #9", ""),
                Equipment("Equipamento #10", "")
        )
    }

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99

    private fun handleIconMapTap() {
        checkLocationPermission()
        startActivity(Intent(this, GymMapsActivity::class.java))
    }

    private fun handleIconStretchingTap() {
        Toast.makeText(application, "Alongamentos", Toast.LENGTH_SHORT).show()
    }

    private fun handleIconEquipmentTap() {
        Toast.makeText(application, "Equipamentos", Toast.LENGTH_SHORT).show()
    }

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
