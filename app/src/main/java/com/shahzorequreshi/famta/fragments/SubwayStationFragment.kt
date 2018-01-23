package com.shahzorequreshi.famta.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shahzorequreshi.famta.R
import android.support.v7.widget.DividerItemDecoration
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.database.entities.SubwayTime
import com.shahzorequreshi.famta.fragments.adapters.SubwayStationRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayStationViewModel
import java.util.Date

/**
 * A fragment representing information about a subway station.
 */
class SubwayStationFragment : Fragment() {
    private lateinit var mSubwayStationViewModel: SubwayStationViewModel
    private var mListener: OnSubwayStationFragmentInteractionListener? = null
    private var mSubwayStationAdapter: SubwayStationRecyclerViewAdapter? = null

    companion object {
        private const val ARG_SUBWAY_STATION = "subway-station"
        private const val ARG_SUBWAY_BOUND = "subway-bound"

        fun newInstance(subwayStation: SubwayStation, subwayBound: SubwayBound): SubwayStationFragment {
            val fragment = SubwayStationFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_STATION, subwayStation)
            args.putSerializable(ARG_SUBWAY_BOUND, subwayBound)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val subwayStation = arguments!![ARG_SUBWAY_STATION] as SubwayStation
            val subwayBound = arguments!![ARG_SUBWAY_BOUND] as SubwayBound
            mSubwayStationViewModel = ViewModelProviders
                    .of(this, SubwayStationViewModel.Factory(subwayStation, subwayBound))
                    .get(SubwayStationViewModel::class.java)
            mSubwayStationViewModel.getSubwayTimes()?.observe(this, Observer { subwayTimes ->
                if(subwayTimes !== null) {
                    mSubwayStationAdapter?.mValues = subwayTimes
                    mSubwayStationAdapter?.notifyDataSetChanged()
                }
            })

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_service, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)

            mSubwayStationAdapter = SubwayStationRecyclerViewAdapter(listOf(), mListener, activity)
            view.adapter = mSubwayStationAdapter

            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayStationFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayStationFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayStationFragmentInteractionListener {
        fun onSubwayTimeClick(item: SubwayTime)
        fun onSubwayTimeExpired(item: SubwayTime)
    }
}
