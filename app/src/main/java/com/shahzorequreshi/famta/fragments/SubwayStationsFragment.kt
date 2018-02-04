package com.shahzorequreshi.famta.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shahzorequreshi.famta.R
import android.support.v7.widget.DividerItemDecoration
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayStationsRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayStationsViewModel

/**
 * A fragment representing subway stations.
 */
class SubwayStationsFragment : Fragment() {
    private lateinit var mSubwayStationsViewModel: SubwayStationsViewModel
    private var mListener: OnSubwayStationsFragmentInteractionListener? = null
    private var mSubwayStationsAdapter: SubwayStationsRecyclerViewAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    private var mRecyclerViewState: Bundle? = null
    private val mRecyclerViewStateKey = "RECYCLER-VIEW"

    companion object {
        const val TAG = "subway_stations_fragment"
        fun newInstance() = SubwayStationsFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSubwayStationsViewModel = ViewModelProviders.of(this).get(SubwayStationsViewModel::class.java)
        mSubwayStationsViewModel.getSubwayStations()?.observe(this, Observer { subwayStations ->
            if(subwayStations !== null && subwayStations.isNotEmpty()) {
                if(subwayStations != mSubwayStationsAdapter?.mValues) {
                    mSubwayStationsAdapter?.mValues = subwayStations
                    mSubwayStationsAdapter?.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_bounds, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            if(mSubwayStationsAdapter == null) {
                mSubwayStationsAdapter = SubwayStationsRecyclerViewAdapter(mListener, context)
            }
            view.adapter = mSubwayStationsAdapter
            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            mRecyclerView = view
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayStationsFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayStationsFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onResume() {
        super.onResume()
        mListener?.onLocationRequest()
        if(mRecyclerViewState != null) {
            val savedState = mRecyclerViewState!!.getParcelable<Parcelable>(mRecyclerViewStateKey)
            mRecyclerView?.layoutManager?.onRestoreInstanceState(savedState)
        }
    }

    override fun onPause() {
        super.onPause()
        mListener?.onCancelLocationRequest()
        mRecyclerViewState = Bundle()
        mRecyclerViewState!!.putParcelable(mRecyclerViewStateKey, mRecyclerView?.layoutManager?.onSaveInstanceState())
    }

    interface OnSubwayStationsFragmentInteractionListener {
        fun onSubwayStationClick(subwayStation: SubwayStation)
        fun onLocationRequest()
        fun onCancelLocationRequest()
    }
}
