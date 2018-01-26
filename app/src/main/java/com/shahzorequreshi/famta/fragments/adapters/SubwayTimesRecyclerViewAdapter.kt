package com.shahzorequreshi.famta.fragments.adapters

import android.content.Context
import android.os.CountDownTimer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.fragment_subway_stations_list_item
import com.shahzorequreshi.famta.database.entities.SubwayTime
import com.shahzorequreshi.famta.fragments.SubwayTimeFragment
import java.util.*

/**
 * [RecyclerView.Adapter] that can display subway times.
 */
class SubwayTimesRecyclerViewAdapter(
        var mValues: List<SubwayTime>,
        private val mListener: SubwayTimeFragment.OnSubwayTimeFragmentInteractionListener?,
        private val mContext: Context?)
    : RecyclerView.Adapter<SubwayTimesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_subway_stations_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mTextView.text = holder.mItem!!.toString()
        holder.mView.setOnClickListener {
            mListener?.onSubwayTimeClick(holder.mItem!!)
        }
        if(position == 0 && mContext != null) {
            val marginTop = mContext.resources.getDimension(R.dimen.bottom_navigation_view_height)
            val marginBottom = mContext.resources.getDimension(R.dimen.activity_vertical_margin)
            val params = holder.mView.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, marginTop.toInt(), 0, marginBottom.toInt())
        }
        if(holder.mCounter != null) {
            holder.mCounter?.cancel()
        }
        holder.mCounter = object: CountDownTimer(holder.mItem!!.arrivalTime.time - Date().time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                holder.mTextView.text = holder.mItem!!.toString()
            }
            override fun onFinish() {
                mListener?.onSubwayTimeExpired(holder.mItem!!)
            }
        }.start()
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextView: TextView = mView as TextView
        var mItem: SubwayTime? = null
        var mCounter: CountDownTimer? = null
    }
}
