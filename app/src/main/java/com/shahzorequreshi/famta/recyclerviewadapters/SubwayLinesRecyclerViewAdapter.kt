package com.shahzorequreshi.famta.recyclerviewadapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.*
import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.fragments.SubwayLinesFragment.OnSubwayLinesFragmentInteractionListener
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayLinesRecyclerViewAdapter.SubwayLineViewTypes.fourServices
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayLinesRecyclerViewAdapter.SubwayLineViewTypes.oneService
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayLinesRecyclerViewAdapter.SubwayLineViewTypes.threeServices
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayLinesRecyclerViewAdapter.SubwayLineViewTypes.twoServices
import com.shahzorequreshi.famta.util.SubwayMaps

/**
 * [RecyclerView.Adapter] that can display subway lines.
 */
class SubwayLinesRecyclerViewAdapter(
        private val mListener: OnSubwayLinesFragmentInteractionListener?,
        private val mContext: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mValues = listOf<SubwayLine>()

    override fun getItemViewType(position: Int): Int {
        return when(mValues[position].name.length) {
            1 -> SubwayLineViewTypes.oneService
            2 -> SubwayLineViewTypes.twoServices
            3 -> if(mValues[position].name == "SIR") SubwayLineViewTypes.oneService else SubwayLineViewTypes.threeServices
            4 -> SubwayLineViewTypes.fourServices
            else -> SubwayLineViewTypes.fourServices
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SubwayLineViewHolderFactory().createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        return SubwayLineViewHolderFactory().bindViewHolder(viewHolder, position)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    private object SubwayLineViewTypes {
        const val oneService = 1
        const val twoServices = 2
        const val threeServices  = 3
        const val fourServices = 4
    }

    private interface SubwayLineViewHolder {
        fun bindViewHolder(position: Int)
    }

    inner class SubwayLineViewHolderFactory {
        fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            when(viewType) {
                oneService -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_lines_list_item_with_one_service, parent, false)
                    return ViewHolderOneService(view)
                }
                twoServices -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_lines_list_item_with_two_services, parent, false)
                    return ViewHolderTwoServices(view)
                }
                threeServices -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_lines_list_item_with_three_services, parent, false)
                    return ViewHolderThreeServices(view)
                }
                fourServices -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_lines_list_item_with_four_services, parent, false)
                    return ViewHolderFourServices(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(fragment_subway_lines_list_item_with_four_services, parent, false)
                    return ViewHolderFourServices(view)
                }
            }
        }

        fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            if(viewHolder is SubwayLineViewHolder) viewHolder.bindViewHolder(position)
        }

        inner class ViewHolderOneService(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayLineViewHolder {
            private var mItem: SubwayLine? = null
            private val mTextView: TextView = mView.findViewById(R.id.subway_line_one_service_text) as TextView
            private val mImageView: ImageView = mView.findViewById(R.id.subway_line_one_service_image)

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.status

                var textColor = -1
                when(mItem!!.status) {
                    "PLANNED WORK" -> textColor = R.color.bronze
                    "SERVICE CHANGE" -> textColor = R.color.bronze
                    "DELAYS" -> textColor = R.color.red
                    "GOOD SERVICE" -> textColor = R.color.green
                }
                if(textColor != -1) {
                    mTextView.setTextColor(ContextCompat.getColor(mContext, textColor))
                }

                mImageView.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name))
                mView.setOnClickListener {
                    if(mItem!!.status != "GOOD SERVICE")
                        mListener?.onSubwayLineClick(mItem as SubwayLine)
                }
            }
        }

        inner class ViewHolderTwoServices(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayLineViewHolder {
            private var mItem: SubwayLine? = null
            private val mTextView: TextView = mView.findViewById(R.id.subway_line_two_services_text) as TextView
            private val mImageViewOne: ImageView = mView.findViewById(R.id.subway_line_two_services_image_one)
            private val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_line_two_services_image_two)

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.status

                var textColor = -1
                when(mItem!!.status) {
                    "PLANNED WORK" -> textColor = R.color.bronze
                    "SERVICE CHANGE" -> textColor = R.color.bronze
                    "DELAYS" -> textColor = R.color.red
                    "GOOD SERVICE" -> textColor = R.color.green
                }
                if(textColor != -1) {
                    mTextView.setTextColor(ContextCompat.getColor(mContext, textColor))
                }

                mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name[0].toString()))
                mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name[1].toString()))
                mView.setOnClickListener {
                    if(mItem!!.status != "GOOD SERVICE")
                        mListener?.onSubwayLineClick(mItem as SubwayLine)
                }
            }
        }

        inner class ViewHolderThreeServices(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayLineViewHolder {
            private var mItem: SubwayLine? = null
            private val mTextView: TextView = mView.findViewById(R.id.subway_line_three_services_text) as TextView
            private val mImageViewOne: ImageView = mView.findViewById(R.id.subway_line_three_services_image_one)
            private val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_line_three_services_image_two)
            private val mImageViewThree: ImageView = mView.findViewById(R.id.subway_line_three_services_image_three)

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.status

                var textColor = -1
                when(mItem!!.status) {
                    "PLANNED WORK" -> textColor = R.color.bronze
                    "SERVICE CHANGE" -> textColor = R.color.bronze
                    "DELAYS" -> textColor = R.color.red
                    "GOOD SERVICE" -> textColor = R.color.green
                }
                if(textColor != -1) {
                    mTextView.setTextColor(ContextCompat.getColor(mContext, textColor))
                }

                mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name[0].toString()))
                mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name[1].toString()))
                mImageViewThree.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name[2].toString()))
                mView.setOnClickListener {
                    if(mItem!!.status != "GOOD SERVICE")
                        mListener?.onSubwayLineClick(mItem as SubwayLine)
                }
            }
        }

        inner class ViewHolderFourServices(private val mView: View) : RecyclerView.ViewHolder(mView), SubwayLineViewHolder {
            private var mItem: SubwayLine? = null
            private val mTextView: TextView = mView.findViewById(R.id.subway_line_four_services_text) as TextView
            private val mImageViewOne: ImageView = mView.findViewById(R.id.subway_line_four_services_image_one)
            private val mImageViewTwo: ImageView = mView.findViewById(R.id.subway_line_four_services_image_two)
            private val mImageViewThree: ImageView = mView.findViewById(R.id.subway_line_four_services_image_three)
            private val mImageViewFour: ImageView = mView.findViewById(R.id.subway_line_four_services_image_four)

            override fun bindViewHolder(position: Int) {
                mItem = mValues[position]
                mTextView.text = mItem!!.status

                var textColor = -1
                when(mItem!!.status) {
                    "PLANNED WORK" -> textColor = R.color.bronze
                    "SERVICE CHANGE" -> textColor = R.color.bronze
                    "DELAYS" -> textColor = R.color.red
                    "GOOD SERVICE" -> textColor = R.color.green
                }
                if(textColor != -1) {
                    mTextView.setTextColor(ContextCompat.getColor(mContext, textColor))
                }

                mImageViewOne.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name[0].toString()))
                mImageViewTwo.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name[1].toString()))
                mImageViewThree.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name[2].toString()))
                mImageViewFour.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mItem!!.name[3].toString()))
                mView.setOnClickListener {
                    if(mItem!!.status != "GOOD SERVICE")
                        mListener?.onSubwayLineClick(mItem as SubwayLine)
                }
            }
        }
    }
}
