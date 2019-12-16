package com.lxj.ankosqlit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lxj.ankosqlit.ListAdapter.CityViewHolder

/**
 * @description AnkoSqlit
 * @author lixianjin
 * create on 2019-11-08 14:58
 */
class ListAdapter: RecyclerView.Adapter<CityViewHolder>() {

    private var mList: List<MainActivity.CityForecast> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        with(mList[position]) {
            holder.city.text = city
            holder.country.text = country
        }
    }

    fun setList(list: List<MainActivity.CityForecast>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class CityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val city: TextView = itemView.findViewById(R.id.tv_city)
        val country: TextView = itemView.findViewById(R.id.tv_country)
    }
}