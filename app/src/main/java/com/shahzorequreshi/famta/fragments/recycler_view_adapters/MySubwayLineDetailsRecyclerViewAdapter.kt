package com.shahzorequreshi.famta.fragments.recycler_view_adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.fragment_subway_line_details_list_item

import com.shahzorequreshi.famta.objects.Subway.SubwayService
import com.shahzorequreshi.famta.fragments.SubwayLineDetailsFragment.OnSubwayLineDetailsFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display a [SubwayLine] and makes a call to the
 * specified [OnFeedFragmentInteractionListener].
 */
class MySubwayLineDetailsRecyclerViewAdapter(
        private val mValues: List<SubwayService?>,
        private val mListener: OnSubwayLineDetailsFragmentInteractionListener?)
    : RecyclerView.Adapter<MySubwayLineDetailsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(fragment_subway_line_details_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mImageView.setImageResource(holder.mItem!!.drawableId)
        holder.mView.setOnClickListener {
            mListener?.onSubwayLineDetailsFragmentInteraction(holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView: ImageView = mView.findViewById(R.id.subway_line_details_list_item) as ImageView
        var mItem: SubwayService? = null

        override fun toString(): String {
            return "${super.toString()} '${mItem?.name}'"
        }
    }
}
