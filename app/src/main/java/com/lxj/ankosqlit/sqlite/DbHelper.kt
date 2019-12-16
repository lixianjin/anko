package com.lxj.ankosqlit.sqlite

import android.database.sqlite.SQLiteDatabase
import com.lxj.ankosqlit.MyApplication
import org.jetbrains.anko.db.*

/**
 * @description test
 * @author lixianjin
 * create on 2019-10-30 17:46
 */
class DbHelper private constructor() : ManagedSQLiteOpenHelper(
    MyApplication.instance,
    DB_NAME, null, DB_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            CityTable.NAME, true,
            CityTable.ID to INTEGER + PRIMARY_KEY,
            CityTable.CITY to TEXT,
            CityTable.COUNTRY to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(CityTable.NAME, true)
        onCreate(db)
    }


    companion object {
        val DB_NAME = "cityList.db"
        val DB_VERSION = 1
        val instance: DbHelper by lazy { DbHelper() }
    }

    object CityTable {
        val NAME = "CityModel"
        val ID = "_id"
        val CITY = "city"
        val COUNTRY = "country"
    }

    class CityModel(map: MutableMap<String, Any?>) {
        var _id: Long by map
        var city: String by map
        var country: String by map

        constructor(id: Long, city: String, country: String)
                : this(HashMap()) {
            this._id = id
            this.city = city
            this.country = country
        }
    }


    class MyRowParser : RowParser<Triple<Long, String, String>> {
        override fun parseRow(columns: Array<Any?>): Triple<Long, String, String> {
            return Triple(columns[0] as Long, columns[1] as String, columns[2] as String)
        }
    }

    class CityRowParser : RowParser<CityModel> {
        override fun parseRow(columns: Array<Any?>): CityModel {
            return CityModel(columns[0] as Long, columns[1] as String, columns[2] as String)
        }
    }
}