//package com.freegym.ej.freegym.database
//
//import android.content.Context
//import android.database.Cursor
//import android.database.SQLException
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteException
//import android.database.sqlite.SQLiteOpenHelper
//import java.io.FileOutputStream
//import java.io.IOException
//
//
//class DatabaseHelper(context: Context, private var DB_PATH: String, private var DB_NAME: String) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
//
//    var myDataBase: SQLiteDatabase? = null
//    private var myContext = context
//    private lateinit var gymDatabase: SQLiteDatabase
//
//    @Throws(IOException::class)
//    fun createDataBase() {
//        val dbExist = checkDataBase()
//        if (dbExist) {
//        } else {
//            this.readableDatabase
////            try {
////                copyDataBase()
////            } catch (e: IOException) {
////                throw Error("Error copying database")
////            }
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
//        return checkDB != null
//    }
//
////    @Throws(IOException::class)
////    private fun copyDataBase() {
////        val myInput = myContext.getAssets().open(DB_NAME)
////        val outFileName = DB_PATH + DB_NAME
////        val myOutput = FileOutputStream(outFileName)
////        val buffer = ByteArray(10)
////        var length: Int = myInput.read(buffer)
////        while (length > 0) {
////            myOutput.write(buffer, 0, length)
////            length = myInput.read(buffer)
////        }
////        myOutput.flush()
////        myOutput.close()
////        myInput.close()
////
////    }
//
//    @Throws(SQLException::class)
//    fun openDataBase() {
//        val myPath = DB_PATH + DB_NAME
//        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
//    }
//
//    @Synchronized
//    override fun close() {
//        if (myDataBase != null)
//            myDataBase!!.close()
//        super.close()
//    }
//
//    override fun onCreate(db: SQLiteDatabase) {}
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
////        if (newVersion > oldVersion)
////            try {
////                copyDataBase()
////            } catch (e: IOException) {
////                e.printStackTrace()
////
////            }
//    }
//
//    fun query(table: String, columns: Array<String>?, selection: String?, selectionArgs: Array<String>?, groupBy: String?, having: String?, orderBy: String?): Cursor {
//        return myDataBase!!.query("Places", null, null, null, null, null, null)
//    }
//}
////class DatabaseHelper(context: Context) {
////    private lateinit var db: SQLiteDatabase
////    private var database: GymDatabase? = null
////
////    fun searchNearestPlaces(currentLocation: LatLng, range: Int): Cursor {
////        db = database!!.readableDatabase
////        var campos = arrayOf("Id", "Nome")
////
////        return db.query("Places", campos, null, null, null, null, null, null)
////    }
////
//////    private static final String NOME_BANCO = "banco.db";
//////    private static final String TABELA = "livros";
//////    private static final String ID = "_id";
//////    private static final String TITULO = "titulo";
//////    private static final String AUTOR = "autor";
//////    private static final String EDITORA = "editora";
//////    private static final int VERSAO = 1;
////
////    //    public Cursor carregaDados(){
//////        Cursor cursor;
//////        String[] campos =  {banco.ID,banco.TITULO};
//////        db = banco.getReadableDatabase();
//////        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);
//////
//////        if(cursor!=null){
//////            cursor.moveToFirst();
//////        }
//////        db.close();
//////        return cursor;
//////    }
////    init {
//////        database = GymDatabase(context, "gym.db.db", "/assets/databases/gym.db.db", null, 1)
////        database = GymDatabase(context)
////    }
////}