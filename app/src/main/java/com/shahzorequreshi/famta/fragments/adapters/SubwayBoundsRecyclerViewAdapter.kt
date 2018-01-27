package com.shahzorequreshi.famta.fragments.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.fragment_subway_bounds_list_item
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.database.entities.SubwayService
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.fragments.SubwayBoundsFragment.OnSubwayBoundsFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display subway service information.
 */
class SubwayBoundsRecyclerViewAdapter(
        var mSubwayStation: SubwayStation? = null,
        var mSubwayService: SubwayService? = null,
        var mValues: List<SubwayBound>,
        private val mListener: OnSubwayBoundsFragmentInteractionListener?,
        private val mContext: Context?)
    : RecyclerView.Adapter<SubwayBoundsRecyclerViewAdapter.ViewHolder>() {

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
        if(position == 0 && mContext != null) {
            val marginTop = mContext.resources.getDimension(R.dimen.bottom_navigation_view_height)
            val marginBottom = mContext.resources.getDimension(R.dimen.activity_vertical_margin)
            val params = holder.mView.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, marginTop.toInt(), 0, marginBottom.toInt())
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
