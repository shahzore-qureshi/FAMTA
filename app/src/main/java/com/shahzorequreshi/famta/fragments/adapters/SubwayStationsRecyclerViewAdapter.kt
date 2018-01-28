package com.shahzorequreshi.famta.fragments.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.*
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.fragments.SubwayStationsFragment.OnSubwayStationsFragmentInteractionListener
import com.shahzorequreshi.famta.util.SubwayMaps

/**
 * [RecyclerView.Adapter] that can display subway stations.
 */
class SubwayStationsRecyclerViewAdapter(
        var mValues: List<SubwayStation>,
        private val mListener: OnSubwayStationsFragmentInteractionListener?,
        private val mContext: Context?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        when(mValues[position].service_ids.size) {
            0 -> return ViewType.zeroServices
            1 -> return ViewType.oneService
            2 -> return ViewType.twoServices
            3 -> return ViewType.threeServices
        }
        return ViewType.zeroServices
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            ViewType.zeroServices -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(fragment_subway_stations_list_item, parent, false)
                return ViewHolderZeroServices(view)
            }
            ViewType.oneService -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(fragment_subway_stations_list_item_with_one_service, parent, false)
                return ViewHolderOneService(view)
            }
            ViewType.twoServices -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(fragment_subway_stations_list_item_with_two_services, parent, false)
                return ViewHolderTwoServices(view)
            }
            ViewType.threeServices -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(fragment_subway_stations_list_item_with_three_services, parent, false)
                return ViewHolderThreeServices(view)
            }
        }
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_subway_stations_list_item, parent, false)
        return ViewHolderZeroServices(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when(viewHolder) {
            is ViewHolderZeroServices -> {
                viewHolder.mItem = mValues[position]
                viewHolder.mTextView.text = viewHolder.mItem!!.toString()
                viewHolder.mView.setOnClickListener {
                    if(viewHolder.mItem is SubwayStation)
                        mListener?.onSubwayStationClick(viewHolder.mItem as SubwayStation)
                }
                if(position == 0 && mContext != null) {
                    val marginTop = mContext.resources.getDimension(R.dimen.bottom_navigation_view_height)
                    val marginBottom = mContext.resources.getDimension(R.dimen.activity_vertical_margin)
                    val params = viewHolder.mView.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(0, marginTop.toInt(), 0, 0)
                }
            }
            is ViewHolderOneService -> {
                viewHolder.mItem = mValues[position]
                viewHolder.mTextView.text = viewHolder.mItem!!.toString()
                viewHolder.mImageView.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(viewHolder.mItem!!.service_ids[0]))
                viewHolder.mView.setOnClickListener {
                    if(viewHolder.mItem is SubwayStation)
                        mListener?.onSubwayStationClick(viewHolder.mItem as SubwayStation)
                }
                if(position == 0 && mContext != null) {
                    val marginTop = mContext.resources.getDimension(R.dimen.bottom_navigation_view_height)
                    val marginBottom = mContext.resources.getDimension(R.dimen.activity_vertical_margin)
                    val params = viewHolder.mView.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(0, marginTop.toInt(), 0, 0)
                }
            }
            is ViewHolderTwoServices -> {
                viewHolder.mItem = mValues[position]
                viewHolder.mTextView.text = viewHolder.mItem!!.toString()
                viewHolder.mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(viewHolder.mItem!!.service_ids[0]))
                viewHolder.mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(viewHolder.mItem!!.service_ids[1]))
                viewHolder.mView.setOnClickListener {
                    if(viewHolder.mItem is SubwayStation)
                        mListener?.onSubwayStationClick(viewHolder.mItem as SubwayStation)
                }
                if(position == 0 && mContext != null) {
                    val marginTop = mContext.resources.getDimension(R.dimen.bottom_navigation_view_height)
                    val marginBottom = mContext.resources.getDimension(R.dimen.activity_vertical_margin)
                    val params = viewHolder.mView.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(0, marginTop.toInt(), 0, 0)
                }
            }
            is ViewHolderThreeServices -> {
                viewHolder.mItem = mValues[position]
                viewHolder.mTextView.text = viewHolder.mItem!!.toString()
                viewHolder.mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(viewHolder.mItem!!.service_ids[0]))
                viewHolder.mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(viewHolder.mItem!!.service_ids[1]))
                viewHolder.mImageViewThree.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(viewHolder.mItem!!.service_ids[2]))
                viewHolder.mView.setOnClickListener {
                    if(viewHolder.mItem is SubwayStation)
                        mListener?.onSubwayStationClick(viewHolder.mItem as SubwayStation)
                }
                if(position == 0 && mContext != null) {
                    val marginTop = mContext.resources.getDimension(R.dimen.bottom_navigation_view_height)
                    val marginBottom = mContext.resources.getDimension(R.dimen.activity_vertical_margin)
                    val params = viewHolder.mView.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(0, marginTop.toInt(), 0, 0)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolderZeroServices(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextView: TextView = mView as TextView
        var mItem: SubwayStation? = null
    }

    inner class ViewHolderOneService(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextView: TextView = mView.findViewById(R.id.subway_station_one_service_text) as TextView
        var mItem: SubwayStation? = null
        val mImageView: ImageView = mView.findViewById(R.id.subway_station_one_service_image)
    }

    inner class ViewHolderTwoServices(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextView: TextView = mView.findViewById(R.id.subway_station_two_services_text) as TextView
        var mItem: SubwayStation? = null
        val mImageViewOne: ImageView = mView.findViewById(R.id.subway_station_two_services_image_one)
        val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_station_two_services_image_two)
    }

    inner class ViewHolderThreeServices(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextView: TextView = mView.findViewById(R.id.subway_station_three_services_text) as TextView
        var mItem: SubwayStation? = null
        val mImageViewOne: ImageView = mView.findViewById(R.id.subway_station_three_services_image_one)
        val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_station_three_services_image_two)
        val mImageViewThree: ImageView = mView.findViewById(R.id.subway_station_three_services_image_three)
    }

    private class ViewType {
        companion object {
            val zeroServices = 0
            val oneService = 1
            val twoServices = 2
            val threeServices  = 3
        }
    }
}
