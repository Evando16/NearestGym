package com.freegym.ej.freegym.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.android.gms.maps.model.LatLng
import android.database.sqlite.SQLiteException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files.exists
import java.nio.file.Files.delete


//class GymDatabase(myContext: Context) : SQLiteOpenHelper(myContext, DB_NAME, null, 1) {
//    override fun onCreate(db: SQLiteDatabase?) {
//
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//    }
//
//    private var DB_PATH: String? = null
//    private var myDataBase: SQLiteDatabase? = null
//
//    init {
//        this.DB_PATH = "/data/data/" + myContext.packageName + "/" + "assets/"
//
//    }
//
//    companion object {
//        private val DB_NAME = "gym"
//    }
//
//    fun createDataBase() {
//        val dbExist = checkDataBase()
//        if (dbExist) {
//        } else {
//            this.readableDatabase
//        }
//    }
//
//    private fun checkDataBase(): Boolean {
//        var checkDB: SQLiteDatabase? = null
//        try {
//            val myPath = DB_PATH + DB_NAME
//            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
//        } catch (e: SQLiteException) {
//        }
//
//        if (checkDB != null) {
//            checkDB.close()
//        }
//        return if (checkDB != null) true else false
//    }
//
//    fun openDataBase() {
//        val myPath = DB_PATH + DB_NAME
//        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
//
//    }
//
//    fun searchNearestPlaces(currentLocation: LatLng, range: Int): Cursor {
//        var campos = arrayOf("Id", "Nome")
//
//        return myDataBase!!.query("Places", campos, null, null, null, null, null, null)
//    }
//
//
//}
//class GymDatabase(context: Context?, name: String?, storageDirectory : String, factory: SQLiteDatabase.CursorFactory?, version: Int)
//    : SQLiteAssetHelper(context, name, storageDirectory, factory, version) {

//    var context: Context? = null
//
//    constructor(context: Context) : super(context, "gym.db", null, 1) {
//
//        this.context = context
//    }
//
//    override fun onCreate(db: SQLiteDatabase?) {
////        var sqlQuery: String = "CREATE TABLE IF NOT EXISTS Places" +
////                "(Id INTEGER PRIMARY KEY," +
////                "Nome TEXT" +
////                "Logradouro TEXT" +
////                "Bairro TEXT" +
////                "Lat INTEGER" +
////                "Long INTEGER)"
////
////
////        db!!.execSQL(sqlQuery);
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//    }
//
////    fun AddPlace(value: ContentValues): String {
////
////        var Msg: String = "error";
////        val ID = sqlObj!!.insert("Places", "", value)
////
////        if (ID > 0) {
////            Msg = "ok"
////        }
////        return Msg
////    }
