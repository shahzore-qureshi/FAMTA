package com.shahzorequreshi.famta.recyclerviewadapters

import android.os.CountDownTimer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shahzorequreshi.famta.R.layout.fragment_subway_times_list_item
import com.shahzorequreshi.famta.database.entities.SubwayTime
import com.shahzorequreshi.famta.fragments.SubwayTimesFragment
import java.util.*

/**
 * [RecyclerView.Adapter] that can display subway times.
 */
class SubwayTimesRecyclerViewAdapter(
        private val mListener: SubwayTimesFragment.OnSubwayTimesFragmentInteractionListener?)
    : RecyclerView.Adapter<SubwayTimesRecyclerViewAdapter.ViewHolder>() {

    var mValues = listOf<SubwayTime>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_subway_times_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mTextView.text = holder.mItem!!.toString()

        if(holder.mCounter != null) {
            holder.mCounter?.cancel()
        }

        val timerDuration = holder.mItem!!.arrival_time - Date().time
        holder.mCounter = object: CountDownTimer(timerDuration, 1000) {
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

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextView: TextView = mView as TextView
        var mItem: SubwayTime? = null
        var mCounter: CountDownTimer? = null
    }
}
