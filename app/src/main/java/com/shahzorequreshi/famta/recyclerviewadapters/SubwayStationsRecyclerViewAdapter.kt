package com.shahzorequreshi.famta.recyclerviewadapters

import android.content.Context
import android.support.v4.view.ViewCompat
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
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayStationsRecyclerViewAdapter.SubwayStationViewTypes.fourServices
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayStationsRecyclerViewAdapter.SubwayStationViewTypes.oneService
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayStationsRecyclerViewAdapter.SubwayStationViewTypes.threeServices
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayStationsRecyclerViewAdapter.SubwayStationViewTypes.twoServices
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayStationsRecyclerViewAdapter.SubwayStationViewTypes.zeroServices
import com.shahzorequreshi.famta.util.SubwayMaps

/**
 * [RecyclerView.Adapter] that can display subway stations.
 */
class SubwayStationsRecyclerViewAdapter(
        private val mListener: OnSubwayStationsFragmentInteractionListener?,
        private val mContext: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mValues = listOf<SubwayStation>()

    override fun getItemViewType(position: Int): Int {
        return when(mValues[position].service_ids.size) {
            0 -> SubwayStationViewTypes.zeroServices
            1 -> SubwayStationViewTypes.oneService
            2 -> SubwayStationViewTypes.twoServices
            3 -> SubwayStationViewTypes.threeServices
            4 -> SubwayStationViewTypes.fourServices
            else -> SubwayStationViewTypes.fourServices
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SubwayStationViewHolderFactory().createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        return SubwayStationViewHolderFactory().bindViewHolder(viewHolder, position)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    private object SubwayStationViewTypes {
        const val zeroServices = 0
        const val oneService = 1
        const val twoServices = 2
        const val threeServices  = 3
        const val fourServices = 4
    }

    private interface SubwayStationViewHolder {
        fun bindViewHolder(position: Int)
    }

    inner class SubwayStationViewHolderFactory {
        fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            when(viewType) {
                zeroServices -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_stations_list_item, parent, false)
                    return ViewHolderZeroServices(view)
                }
                oneService -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_stations_list_item_with_one_service, parent, false)
                    return ViewHolderOneService(view)
                }
                twoServices -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_stations_list_item_with_two_services, parent, false)
                    return ViewHolderTwoServices(view)
                }
                threeServices -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_stations_list_item_with_three_services, parent, false)
                    return ViewHolderThreeServices(view)
                }
                fourServices -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_stations_list_item_with_four_services, parent, false)
                    return ViewHolderFourServices(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_stations_list_item, parent, false)
                    return ViewHolderZeroServices(view)
                }
            }
        }

        fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            if(viewHolder is SubwayStationViewHolder) viewHolder.bindViewHolder(position)
        }

        inner class ViewHolderZeroServices(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayStationViewHolder {
            private var mItem: SubwayStation? = null
            private val mTextView = mView.findViewById(R.id.subway_station_zero_services_text) as TextView
            private val mDistanceView = mView.findViewById(R.id.subway_station_zero_services_distance) as TextView

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.toString()
                if(mItem?.distanceFromUser == 0.0) {
                    mDistanceView.text = ""
                } else {
                    mDistanceView.text = mContext.getString(R.string.distance_miles, mItem?.distanceFromUser)
                }
                mView.setOnClickListener {
                    if (mItem is SubwayStation)
                        mListener?.onSubwayStationClick(mItem as SubwayStation, listOf())
                }
            }
        }

        inner class ViewHolderOneService(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayStationViewHolder {
            private var mItem: SubwayStation? = null
            private val mTextView: TextView = mView.findViewById(R.id.subway_station_one_service_text) as TextView
            private val mImageView: ImageView = mView.findViewById(R.id.subway_station_one_service_image)
            private val mDistanceView = mView.findViewById(R.id.subway_station_one_service_distance) as TextView

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.toString()
                mImageView.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[0]))
                ViewCompat.setTransitionName(mImageView, "service-1")
                if(mItem?.distanceFromUser == 0.0) {
                    mDistanceView.text = ""
                } else {
                    mDistanceView.text = mContext.getString(R.string.distance_miles, mItem?.distanceFromUser)
                }
                mView.setOnClickListener {
                    if(mItem is SubwayStation)
                        mListener?.onSubwayStationClick(mItem as SubwayStation, listOf(mImageView))
                }
            }
        }

        inner class ViewHolderTwoServices(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayStationViewHolder {
            private var mItem: SubwayStation? = null
            private val mTextView: TextView = mView.findViewById(R.id.subway_station_two_services_text) as TextView
            private val mImageViewOne: ImageView = mView.findViewById(R.id.subway_station_two_services_image_one)
            private val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_station_two_services_image_two)
            private val mDistanceView = mView.findViewById(R.id.subway_station_two_services_distance) as TextView

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.toString()
                mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[0]))
                mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[1]))
                ViewCompat.setTransitionName(mImageViewOne, "service-1")
                ViewCompat.setTransitionName(mImageViewTwo, "service-2")
                if(mItem?.distanceFromUser == 0.0) {
                    mDistanceView.text = ""
                } else {
                    mDistanceView.text = mContext.getString(R.string.distance_miles, mItem?.distanceFromUser)
                }
                mView.setOnClickListener {
                    if(mItem is SubwayStation)
                        mListener?.onSubwayStationClick(mItem as SubwayStation, listOf(mImageViewOne, mImageViewTwo))
                }
            }
        }

        inner class ViewHolderThreeServices(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayStationViewHolder {
            private var mItem: SubwayStation? = null
            private val mTextView: TextView = mView.findViewById(R.id.subway_station_three_services_text) as TextView
            private val mImageViewOne: ImageView = mView.findViewById(R.id.subway_station_three_services_image_one)
            private val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_station_three_services_image_two)
            private val mImageViewThree: ImageView = mView.findViewById(R.id.subway_station_three_services_image_three)
            private val mDistanceView = mView.findViewById(R.id.subway_station_three_services_distance) as TextView

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.toString()
                mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[0]))
                mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[1]))
                mImageViewThree.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[2]))
                ViewCompat.setTransitionName(mImageViewOne, "service-1")
                ViewCompat.setTransitionName(mImageViewTwo, "service-2")
                ViewCompat.setTransitionName(mImageViewThree, "service-3")
                if(mItem?.distanceFromUser == 0.0) {
                    mDistanceView.text = ""
                } else {
                    mDistanceView.text = mContext.getString(R.string.distance_miles, mItem?.distanceFromUser)
                }
                mView.setOnClickListener {
                    if(mItem is SubwayStation)
                        mListener?.onSubwayStationClick(mItem as SubwayStation,
                                listOf(mImageViewOne, mImageViewTwo, mImageViewThree))
                }
            }
        }

        inner class ViewHolderFourServices(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayStationViewHolder {
            private var mItem: SubwayStation? = null
            private val mTextView: TextView = mView.findViewById(R.id.subway_station_four_services_text) as TextView
            private val mImageViewOne: ImageView = mView.findViewById(R.id.subway_station_four_services_image_one)
            private val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_station_four_services_image_two)
            private val mImageViewThree: ImageView = mView.findViewById(R.id.subway_station_four_services_image_three)
            private val mImageViewFour: ImageView = mView.findViewById(R.id.subway_station_four_services_image_four)
            private val mDistanceView = mView.findViewById(R.id.subway_station_four_services_distance) as TextView

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.toString()
                mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[0]))
                mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[1]))
                mImageViewThree.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[2]))
                mImageViewFour.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[3]))
                ViewCompat.setTransitionName(mImageViewOne, "service-1")
                ViewCompat.setTransitionName(mImageViewTwo, "service-2")
                ViewCompat.setTransitionName(mImageViewThree, "service-3")
                ViewCompat.setTransitionName(mImageViewFour, "service-4")
                if(mItem?.distanceFromUser == 0.0) {
                    mDistanceView.text = ""
                } else {
                    mDistanceView.text = mContext.getString(R.string.distance_miles, mItem?.distanceFromUser)
                }
                mView.setOnClickListener {
                    if(mItem is SubwayStation)
                        mListener?.onSubwayStationClick(mItem as SubwayStation,
                                listOf(mImageViewOne, mImageViewTwo, mImageViewThree, mImageViewFour))
                }
            }
        }
    }
}
