package com.shahzorequreshi.famta.fragments.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.fragment_subway_list_item
import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.fragments.SubwayFragment.OnSubwayFragmentInteractionListener
import com.shahzorequreshi.famta.util.SubwayMaps

/**
 * [RecyclerView.Adapter] that can display information about a subway.
 */
class SubwayRecyclerViewAdapter(
        var mValues: List<SubwayLine>,
        private val mListener: OnSubwayFragmentInteractionListener?,
        private val mContext: Context?)
    : RecyclerView.Adapter<SubwayRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_subway_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mImageView.setImageResource(SubwayMaps.getDrawableIdForSubwayLine(holder.mItem!!))
        holder.mView.setOnClickListener {
            mListener?.onSubwayFragmentInteraction(holder.mItem!!)
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
        val mImageView: ImageView = mView.findViewById(R.id.subway_list_item) as ImageView
        var mItem: SubwayLine? = null

        override fun toString(): String {
            return "${super.toString()} '${mItem?.name}'"
        }
    }
}
