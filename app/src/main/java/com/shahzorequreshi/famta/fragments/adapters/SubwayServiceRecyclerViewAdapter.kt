package com.shahzorequreshi.famta.fragments.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.fragment_subway_service_list_item
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.fragments.SubwayServiceFragment.OnSubwayServiceFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display subway service information.
 */
class SubwayServiceRecyclerViewAdapter(
        private val mValues: List<SubwayBound>,
        private val mListener: OnSubwayServiceFragmentInteractionListener?,
        private val mContext: Context?)
    : RecyclerView.Adapter<SubwayServiceRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_subway_service_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]

        var directionLabel = ""
        when(holder.mItem!!.direction) {
            "N" -> directionLabel = "North - "
            "S" -> directionLabel = "South - "
            "E" -> directionLabel = "East - "
            "W" -> directionLabel = "West - "
        }

        holder.mTextView.text = directionLabel + holder.mItem!!.name
        holder.mView.setOnClickListener {
            mListener?.onSubwayServiceFragmentInteraction(holder.mItem!!)
        }
        if(position == 0 && mContext != null) {
            val marginTop = mContext.resources.getDimension(R.dimen.bottom_navigation_view_height)
            val marginBottom = mContext.resources.getDimension(R.dimen.activity_vertical_margin)
            var params = holder.mView.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, marginTop.toInt(), 0, marginBottom.toInt())
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextView: TextView = mView.findViewById(R.id.subway_service_list_item) as TextView
        var mItem: SubwayBound? = null

        override fun toString(): String {
            return "${super.toString()} '${mItem?.name}'"
        }
    }
}
