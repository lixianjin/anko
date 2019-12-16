package com.lxj.ankosqlit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.ankosqlit.sqlite.DbHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast

/**
 *
 *
 * DbHelper.CityTable.CITY to getCity()
 *
 * 和
 *
 * ${DbHelper.CityTable.CITY} = ?", getCity()
 *
 * 2种方式都是可以的，其他字段同理
 *
 * 例如 @see selectWithWhere
 *
 *
 */
class MainActivity : AppCompatActivity() {

    private val forecastDbHelper = DbHelper.instance
    private lateinit var mAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter = ListAdapter()
        rv_recycler.layoutManager = LinearLayoutManager(this)
        rv_recycler.adapter = mAdapter

        select()

        // 插入
        btn_insert.setOnClickListener {
            if (getCity().isEmpty() || getCountry().isEmpty()) {
                toast("插入数据为空")
                return@setOnClickListener
            }
            forecastDbHelper.use {
                val insert = insert(
                    DbHelper.CityTable.NAME,
//                    DbHelper.CityTable.ID to 1,
                    DbHelper.CityTable.CITY to getCity(),
                    DbHelper.CityTable.COUNTRY to getCountry()
                )
                if (insert != -1L) {
                    setEmpty()
                    toast("插入的行数是：$insert")
                }
            }
        }

        // 查询
        btn_select.setOnClickListener {
            if (getCity().isEmpty()) {
                // 列表
                select()
            } else {
                // 条件
                selectWithWhere()
            }
        }

        // 删除
        btn_delete.setOnClickListener {
            if (getCity().isEmpty()) {
                toast("删除数据为空")
                return@setOnClickListener
            }
            forecastDbHelper.use {
                val delete = delete(
                    DbHelper.CityTable.NAME,
                    "city = {city}",
                    DbHelper.CityTable.CITY to getCity()
                )
                Log.e("LXJ", "delete------$delete")
                toast("删除的总行数是：$delete")
                setEmpty()
                delete
            }
        }

        // 修改
        btn_update.setOnClickListener {
            if (getCity().isEmpty() || getCountry().isEmpty()) {
                toast("修改数据为空")
                return@setOnClickListener
            }
            forecastDbHelper.use {
                val update = update(
                    DbHelper.CityTable.NAME,
                    DbHelper.CityTable.COUNTRY to getCountry()
                )
                    .where("city = {city}", "city" to getCity())
                    .exec()
                Log.e("LXJ", "update------$update")
                toast("更新的数量是：$update")
                setEmpty()
                update
            }
        }
    }

    private fun getCity() = et_city.text.toString()

    private fun getCountry() = et_country.text.toString()

    private fun setEmpty() {
        et_city.setText("")
        et_country.setText("")
    }

    /**
     * 查询(列表)
     *
     * git wiki中是由 classParser<CityModel>() 代替 rowParser(::CityModel)
     * 这样会报错，解决方案 "https://github.com/Kotlin/anko/issues/320"
     *
     * 通过自定义扩展函数方式实现
     * @see select2
     */
    private fun select() {
        forecastDbHelper.use {
            val city = select(DbHelper.CityTable.NAME)
                .parseList(rowParser(::CityForecast))

            Log.e("LXJ", "city------${city.size}")

            mAdapter.setList(city)
        }
    }

    /**
     * 查询(按条件查询对象)
     *
     * 通过自定义扩展函数方式实现
     * @see selectWithWhere2
     */
    private fun selectWithWhere() {
        forecastDbHelper.use {
            val cityRequest = "city = {city}"

            select(DbHelper.CityTable.NAME)
//                    .whereSimple("${DbHelper.CityTable.CITY} = ?", getCity())  // 2中方式
                .where(cityRequest, DbHelper.CityTable.CITY to getCity()) // 都可以
                .parseOpt(rowParser(::CityForecast))
                ?.let {
                    Log.e("LXJ", "city------" + it.country)
                    mAdapter.setList(listOf(it))
                }
        }
    }

    /**
     * 通过自定义扩展函数实现
     */
    private fun select2() {
        // 查询(列表)
        forecastDbHelper.use {
            val city = select(DbHelper.CityTable.NAME)
                .parseList { DbHelper.CityModel(HashMap(it)) }

            Log.e("LXJ", "city---delete---${city.size}")
        }
    }

    /**
     * 通过自定义扩展函数实现
     */
    private fun selectWithWhere2() {
        // 查询(按条件查询对象)
        forecastDbHelper.use {
            val cityRequest = "city = {city}"

            val cityForecast = select(DbHelper.CityTable.NAME)
//                    .whereSimple("${DbHelper.CityTable.CITY} = ?", getCity())  // 2中方式
                .where(cityRequest, DbHelper.CityTable.CITY to getCity()) // 都可以
                .parseOpt { DbHelper.CityModel(HashMap(it)) }
            Log.e("LXJ", "city------" + cityForecast?.country)
        }
    }

    /**
     * other fun
     */
    private fun otherFun() {
        // 查询(某一列)
        forecastDbHelper.use {
            select(DbHelper.CityTable.NAME, "city").exec {
                // Doing some stuff with emails
            }
        }
    }

    fun <T : Any> SelectQueryBuilder.parseList(
        parser: (Map<String, Any?>) -> T
    ): List<T> =
        parseList(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
        })


    fun <T : Any> SelectQueryBuilder.parseOpt(
        parser: (Map<String, Any?>) -> T
    ): T? =
        parseOpt(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
        })


    class CityForecast(
        val _id: Long,
        val city: String,
        val country: String
    )
}
