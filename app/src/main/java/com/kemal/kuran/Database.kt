package com.kemal.kuran

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//val SAYFA_NO = "sayfa_no"
//val SECDE_MI = "secde_mi"

val DATABASENAME = "my database"
val TABLENAME = "quran"
val ID = "id"
val CUZ_NO = "cuzNo"
val HIZB_NO = "hizbNo"
val SURE_NO = "sureNo"
val SURE_NAME = "sureName"
val AYET_NO = "ayetNo"
val AYET = "ayet"
val EZBER_DURUMU = "ezberDurumu"
val EZBER_TARIHI = "ezberTarihi"

class Database (var context: Context): SQLiteOpenHelper(context, DATABASENAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CUZ_NO + " INTEGER," + HIZB_NO + " INTEGER," + SURE_NO + " INTEGER," + SURE_NAME + " TEXT," + AYET_NO + " INTEGER," + AYET + " TEXT," + EZBER_DURUMU + " INTEGER," + EZBER_TARIHI + " TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insert(ayet: Ayet){
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CUZ_NO, ayet.cuz_no)
        contentValues.put(HIZB_NO, ayet.hizb_no)
        contentValues.put(SURE_NO, ayet.sure_no)
        contentValues.put(SURE_NAME, ayet.sure_name)
        contentValues.put(AYET_NO, ayet.ayet_no)
        contentValues.put(AYET, ayet.ayet)
        contentValues.put(EZBER_DURUMU, ayet.ezber_durumu)
        contentValues.put(EZBER_TARIHI, ayet.ezber_tarihi)
        val result = database.insert(TABLENAME, null, contentValues)
        /*if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }*/
        database.close()
    }

    fun update(id:String, checkNew:String, date: String){
        val db = this.writableDatabase

        //Eskisimi buluyoruz.
        val query = "Select * from $TABLENAME WHERE $ID = ?"
        val result = db.rawQuery(query, arrayOf(id))
        val ayet = Ayet()
        if (result.moveToFirst()) {
            do {
                ayet.id = result.getString(result.getColumnIndex(ID)).toInt()
                ayet.cuz_no = result.getString(result.getColumnIndex(CUZ_NO)).toInt()
                ayet.hizb_no = result.getString(result.getColumnIndex(HIZB_NO)).toInt()
                ayet.sure_no = result.getString(result.getColumnIndex(SURE_NO)).toInt()
                ayet.sure_name = result.getString(result.getColumnIndex(SURE_NAME))
                ayet.ayet_no = result.getString(result.getColumnIndex(AYET_NO)).toInt()
                ayet.ayet = result.getString(result.getColumnIndex(AYET))
                ayet.ezber_durumu = result.getString(result.getColumnIndex(EZBER_DURUMU)).toInt()
                ayet.ezber_tarihi = result.getString(result.getColumnIndex(EZBER_TARIHI))
            }
            while (result.moveToNext())
        }

        //Yeni ezber durumu va tarihi giriyoruz.
        val contentValues = ContentValues()
        contentValues.put(ID, ayet.id)
        contentValues.put(CUZ_NO, ayet.cuz_no)
        contentValues.put(HIZB_NO, ayet.hizb_no)
        contentValues.put(SURE_NO, ayet.sure_no)
        contentValues.put(SURE_NAME, ayet.sure_name)
        contentValues.put(AYET_NO, ayet.ayet_no)
        contentValues.put(AYET, ayet.ayet)
        contentValues.put(EZBER_DURUMU, checkNew.toInt())
        contentValues.put(EZBER_TARIHI, date)
        db.update(TABLENAME, contentValues, "ID = ?", arrayOf(id))

        db.close()
    }



    fun readAll(): MutableList<Ayet>{
        val list: MutableList<Ayet> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val ayet = Ayet()
                ayet.id = result.getString(result.getColumnIndex(ID)).toInt()
                ayet.cuz_no = result.getString(result.getColumnIndex(CUZ_NO)).toInt()
                ayet.hizb_no = result.getString(result.getColumnIndex(HIZB_NO)).toInt()
                ayet.sure_no = result.getString(result.getColumnIndex(SURE_NO)).toInt()
                ayet.sure_name = result.getString(result.getColumnIndex(SURE_NAME))
                ayet.ayet_no = result.getString(result.getColumnIndex(AYET_NO)).toInt()
                ayet.ayet = result.getString(result.getColumnIndex(AYET))
                ayet.ezber_durumu = result.getString(result.getColumnIndex(EZBER_DURUMU)).toInt()
                ayet.ezber_tarihi = result.getString(result.getColumnIndex(EZBER_TARIHI))
                list.add(ayet)
            }
            while (result.moveToNext())
        }
        db.close()
        return list
    }

    fun readSurah(sureNo: String): MutableList<Ayet>{
        val list: MutableList<Ayet> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME WHERE $SURE_NO = ?"
        val result = db.rawQuery(query, arrayOf(sureNo))
        if (result.moveToFirst()) {
            do {
                val ayet = Ayet()
                ayet.id = result.getString(result.getColumnIndex(ID)).toInt()
                ayet.cuz_no = result.getString(result.getColumnIndex(CUZ_NO)).toInt()
                ayet.hizb_no = result.getString(result.getColumnIndex(HIZB_NO)).toInt()
                ayet.sure_no = result.getString(result.getColumnIndex(SURE_NO)).toInt()
                ayet.sure_name = result.getString(result.getColumnIndex(SURE_NAME))
                ayet.ayet_no = result.getString(result.getColumnIndex(AYET_NO)).toInt()
                ayet.ayet = result.getString(result.getColumnIndex(AYET))
                ayet.ezber_durumu = result.getString(result.getColumnIndex(EZBER_DURUMU)).toInt()
                ayet.ezber_tarihi = result.getString(result.getColumnIndex(EZBER_TARIHI))
                list.add(ayet)
            }
            while (result.moveToNext())
        }
        return list
    }

    fun oranSurah(sureNo: String): Oran{
        val list: MutableList<Ayet> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME WHERE $SURE_NO = ?"
        val result = db.rawQuery(query, arrayOf(sureNo))
        var ezberlenmis = 0
        var all = 0
        if (result.moveToFirst()) {
            do {
                if(result.getString(result.getColumnIndex(EZBER_DURUMU)).toInt() == 1) ezberlenmis++
                all++
            }
            while (result.moveToNext())
        }
        val oran = Oran()
        oran.ezberlenmis = ezberlenmis
        oran.all = all
        return oran
    }

    fun oranHizb(hizbNo: String): Oran{
        val list: MutableList<Ayet> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME WHERE $HIZB_NO = ?"
        val result = db.rawQuery(query, arrayOf(hizbNo))
        var ezberlenmis = 0
        var all = 0
        if (result.moveToFirst()) {
            do {
                if(result.getString(result.getColumnIndex(EZBER_DURUMU)).toInt() == 1) ezberlenmis++
                all++
            }
            while (result.moveToNext())
        }
        val oran = Oran()
        oran.ezberlenmis = ezberlenmis
        oran.all = all
        return oran
    }

    fun oranAll(): Oran{
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        var ezberlenmis = 0
        var all = 0
        if (result.moveToFirst()) {
            do {
                if(result.getString(result.getColumnIndex(EZBER_DURUMU)).toInt() == 1) ezberlenmis++
                all++
            }
            while (result.moveToNext())
        }
        val oran = Oran()
        oran.ezberlenmis = ezberlenmis
        oran.all = all
        return oran
    }

    fun readHizb(hizbNo: String): MutableList<Ayet>{
        val list: MutableList<Ayet> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME WHERE $HIZB_NO = ?"
        val result = db.rawQuery(query, arrayOf(hizbNo))
        if (result.moveToFirst()) {
            do {
                val ayet = Ayet()
                ayet.id = result.getString(result.getColumnIndex(ID)).toInt()
                ayet.cuz_no = result.getString(result.getColumnIndex(CUZ_NO)).toInt()
                ayet.hizb_no = result.getString(result.getColumnIndex(HIZB_NO)).toInt()
                ayet.sure_no = result.getString(result.getColumnIndex(SURE_NO)).toInt()
                ayet.sure_name = result.getString(result.getColumnIndex(SURE_NAME))
                ayet.ayet_no = result.getString(result.getColumnIndex(AYET_NO)).toInt()
                ayet.ayet = result.getString(result.getColumnIndex(AYET))
                ayet.ezber_durumu = result.getString(result.getColumnIndex(EZBER_DURUMU)).toInt()
                ayet.ezber_tarihi = result.getString(result.getColumnIndex(EZBER_TARIHI))
                list.add(ayet)
            }
            while (result.moveToNext())
        }
        return list
    }


}