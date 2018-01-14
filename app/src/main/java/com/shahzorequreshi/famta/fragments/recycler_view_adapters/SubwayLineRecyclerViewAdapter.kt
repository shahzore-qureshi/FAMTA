package com.shahzorequreshi.famta.fragments.recycler_view_adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.fragment_subway_line_list_item

import com.shahzorequreshi.famta.objects.Subway.SubwayService
import com.shahzorequreshi.famta.fragments.SubwayLineFragment.OnSubwayLineFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display a [SubwayLine] and makes a call to the
 * specified [OnFeedFragmentInteractionListener].
 */
class SubwayLineRecyclerViewAdapter(
        private val mValues: List<SubwayService?>,
        private val mListener: OnSubwayLineFragmentInteractionListener?,
        private val mContext: Context?)
    : RecyclerView.Adapter<SubwayLineRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(fragment_subway_line_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mImageView.setImageResource(holder.mItem!!.drawableId)
        holder.mView.setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(holder.mItem!!)
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
        val mImageView: ImageView = mView.findViewById(R.id.subway_line_list_item) as ImageView
        var mItem: SubwayService? = null

        override fun toString(): String {
            return "${super.toString()} '${mItem?.name}'"
        }
    }
}
