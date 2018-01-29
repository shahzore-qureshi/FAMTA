package com.shahzorequreshi.famta.fragments.adapters

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
import com.shahzorequreshi.famta.fragments.adapters.SubwayStationsRecyclerViewAdapter.SubwayStationViewTypes.oneService
import com.shahzorequreshi.famta.fragments.adapters.SubwayStationsRecyclerViewAdapter.SubwayStationViewTypes.threeServices
import com.shahzorequreshi.famta.fragments.adapters.SubwayStationsRecyclerViewAdapter.SubwayStationViewTypes.twoServices
import com.shahzorequreshi.famta.fragments.adapters.SubwayStationsRecyclerViewAdapter.SubwayStationViewTypes.zeroServices
import com.shahzorequreshi.famta.util.SubwayMaps

/**
 * [RecyclerView.Adapter] that can display subway stations.
 */
class SubwayStationsRecyclerViewAdapter(private val mListener: OnSubwayStationsFragmentInteractionListener?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mValues = listOf<SubwayStation>()
        set(newValues) {
            //Add empty station to the top of the list
            //to compensate for the height of the app
            //navigation bar. The bar covers the top
            //of the list.
            val emptyStation = SubwayStation("", "", "", 0.0, 0.0, listOf())
            val newList = mutableListOf(emptyStation)
            newList.addAll(newValues)
            field = newList
        }

    override fun getItemViewType(position: Int): Int {
        when(mValues[position].service_ids.size) {
            0 -> return SubwayStationViewTypes.zeroServices
            1 -> return SubwayStationViewTypes.oneService
            2 -> return SubwayStationViewTypes.twoServices
            3 -> return SubwayStationViewTypes.threeServices
        }
        return SubwayStationViewTypes.zeroServices
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
            private val mTextView: TextView = mView as TextView
            private var mItem: SubwayStation? = null

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.toString()
                mView.setOnClickListener {
                    if (mItem is SubwayStation)
                        mListener?.onSubwayStationClick(mItem as SubwayStation)
                }
            }
        }

        inner class ViewHolderOneService(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayStationViewHolder {
            private val mTextView: TextView = mView.findViewById(R.id.subway_station_one_service_text) as TextView
            private var mItem: SubwayStation? = null
            private val mImageView: ImageView = mView.findViewById(R.id.subway_station_one_service_image)

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.toString()
                mImageView.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[0]))
                mView.setOnClickListener {
                    if(mItem is SubwayStation)
                        mListener?.onSubwayStationClick(mItem as SubwayStation)
                }
            }
        }

        inner class ViewHolderTwoServices(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayStationViewHolder {
            private val mTextView: TextView = mView.findViewById(R.id.subway_station_two_services_text) as TextView
            private var mItem: SubwayStation? = null
            private val mImageViewOne: ImageView = mView.findViewById(R.id.subway_station_two_services_image_one)
            private val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_station_two_services_image_two)

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.toString()
                mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[0]))
                mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[1]))
                mView.setOnClickListener {
                    if(mItem is SubwayStation)
                        mListener?.onSubwayStationClick(mItem as SubwayStation)
                }
            }
        }

        inner class ViewHolderThreeServices(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayStationViewHolder {
            private val mTextView: TextView = mView.findViewById(R.id.subway_station_three_services_text) as TextView
            private var mItem: SubwayStation? = null
            private val mImageViewOne: ImageView = mView.findViewById(R.id.subway_station_three_services_image_one)
            private val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_station_three_services_image_two)
            private val mImageViewThree: ImageView = mView.findViewById(R.id.subway_station_three_services_image_three)

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.toString()
                mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[0]))
                mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[1]))
                mImageViewThree.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.service_ids[2]))
                mView.setOnClickListener {
                    if(mItem is SubwayStation)
                        mListener?.onSubwayStationClick(mItem as SubwayStation)
                }
            }
        }
    }
}
