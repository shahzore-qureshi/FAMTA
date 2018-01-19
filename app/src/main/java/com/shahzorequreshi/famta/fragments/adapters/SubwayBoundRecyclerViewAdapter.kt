package com.shahzorequreshi.famta.fragments.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.fragment_subway_bound_list_item
import com.shahzorequreshi.famta.database.objects.SubwayStation
import com.shahzorequreshi.famta.fragments.SubwayBoundFragment.OnSubwayBoundFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display subway bound information.
 */
class SubwayBoundRecyclerViewAdapter(
        private val mValues: List<SubwayStation>,
        private val mListener: OnSubwayBoundFragmentInteractionListener?,
        private val mContext: Context?)
    : RecyclerView.Adapter<SubwayBoundRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_subway_bound_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mTextView.text = holder.mItem!!.name
        holder.mView.setOnClickListener {
            mListener?.onSubwayBoundFragmentInteraction(holder.mItem!!)
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
        val mTextView: TextView = mView.findViewById(R.id.subway_bound_list_item) as TextView
        var mItem: SubwayStation? = null

        override fun toString(): String {
            return "${super.toString()} '${mItem?.name}'"
        }
    }
}
