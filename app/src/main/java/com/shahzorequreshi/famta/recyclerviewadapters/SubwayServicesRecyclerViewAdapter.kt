package com.shahzorequreshi.famta.recyclerviewadapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.shahzorequreshi.famta.R.layout.fragment_subway_services_list_item
import com.shahzorequreshi.famta.database.entities.SubwayService
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.fragments.SubwayServicesFragment.OnSubwayServicesFragmentInteractionListener
import com.shahzorequreshi.famta.util.SubwayMaps

/**
 * [RecyclerView.Adapter] that can show subway services.
 */
class SubwayServicesRecyclerViewAdapter(
        private val mListener: OnSubwayServicesFragmentInteractionListener?)
    : RecyclerView.Adapter<SubwayServicesRecyclerViewAdapter.ViewHolder>() {

    var mSubwayStation: SubwayStation? = null
    var mValues = listOf<SubwayService>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_subway_services_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mImageView.setImageResource(SubwayMaps.getDrawableIdForSubwayService(holder.mItem!!))
        holder.mView.setOnClickListener {
            mListener?.onSubwayServiceClick(mSubwayStation!!, holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView: ImageView = mView as ImageView
        var mItem: SubwayService? = null
    }
}
