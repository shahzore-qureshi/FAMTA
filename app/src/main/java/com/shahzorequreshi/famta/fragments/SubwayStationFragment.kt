package com.shahzorequreshi.famta.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.objects.Subway.SubwayBound
import android.support.v7.widget.DividerItemDecoration
import com.shahzorequreshi.famta.fragments.recycler_view_adapters.SubwayBoundRecyclerViewAdapter
import com.shahzorequreshi.famta.fragments.recycler_view_adapters.SubwayStationRecyclerViewAdapter
import com.shahzorequreshi.famta.objects.Subway.SubwayStation
import java.util.Date

/**
 * A fragment representing information about a subway station.
 */
class SubwayStationFragment : Fragment() {
    private var mSubwayStation: SubwayStation? = null
    private var mListener: OnSubwayStationFragmentInteractionListener? = null

    companion object {
        private val ARG_SUBWAY_STATION = "subway-station"

        fun newInstance(subwayStation: SubwayStation): SubwayStationFragment {
            val fragment = SubwayStationFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_STATION, subwayStation)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mSubwayStation = arguments[ARG_SUBWAY_STATION] as SubwayStation
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_subway_service, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            view.adapter = SubwayStationRecyclerViewAdapter(mSubwayStation!!.arrivalTimes, mListener, activity)
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
        fun onSubwayStationFragmentInteraction(item: Date)
    }
}
