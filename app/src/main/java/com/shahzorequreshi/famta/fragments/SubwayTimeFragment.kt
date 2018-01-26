package com.shahzorequreshi.famta.fragments

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
import com.shahzorequreshi.famta.database.entities.SubwayTime
import com.shahzorequreshi.famta.fragments.adapters.SubwayTimesRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayStationsViewModel

/**
 * A fragment representing information about a subway station.
 */
class SubwayTimeFragment : Fragment() {
    private lateinit var mSubwayStationViewModel: SubwayStationsViewModel
    private var mListener: OnSubwayTimeFragmentInteractionListener? = null
    private var mSubwayStationAdapter: SubwayTimesRecyclerViewAdapter? = null

    companion object {
        fun newInstance() = SubwayTimeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSubwayStationViewModel = ViewModelProviders
                .of(this, SubwayStationsViewModel.Factory())
                .get(SubwayStationsViewModel::class.java)
        /*
        mSubwayStationViewModel.getSubwayStations()?.observe(this, Observer { subwayStations ->
            if(subwayStations !== null) {
                mSubwayStationAdapter?.mValues = subwayStations
                mSubwayStationAdapter?.notifyDataSetChanged()
            }
        })
        */
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_services, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)

            mSubwayStationAdapter = SubwayTimesRecyclerViewAdapter(listOf(), mListener, activity)
            view.adapter = mSubwayStationAdapter

            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayTimeFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayTimeFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayTimeFragmentInteractionListener {
        fun onSubwayTimeClick(item: SubwayTime)
        fun onSubwayTimeExpired(item: SubwayTime)
    }
}
