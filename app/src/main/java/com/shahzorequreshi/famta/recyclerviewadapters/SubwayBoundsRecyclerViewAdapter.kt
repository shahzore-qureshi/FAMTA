package com.shahzorequreshi.famta.recyclerviewadapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shahzorequreshi.famta.R.layout.fragment_subway_bounds_list_item
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.database.entities.SubwayService
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.fragments.SubwayBoundsFragment.OnSubwayBoundsFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display subway service information.
 */
class SubwayBoundsRecyclerViewAdapter(
        private val mListener: OnSubwayBoundsFragmentInteractionListener?)
    : RecyclerView.Adapter<SubwayBoundsRecyclerViewAdapter.ViewHolder>() {

    var mSubwayStation: SubwayStation? = null
    var mSubwayService: SubwayService? = null
    var mValues = listOf<SubwayBound>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_subway_bounds_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mTextView.text = holder.mItem!!.toString()
        holder.mView.setOnClickListener {
            mListener?.onSubwayBoundClick(mSubwayStation!!, mSubwayService!!, holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextView: TextView = mView as TextView
        var mItem: SubwayBound? = null
    }
}
