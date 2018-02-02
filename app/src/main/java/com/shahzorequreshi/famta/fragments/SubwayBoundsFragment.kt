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
import com.shahzorequreshi.famta.database.entities.SubwayService
import com.shahzorequreshi.famta.database.entities.SubwayBound
import android.support.v7.widget.DividerItemDecoration
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayBoundsRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayBoundsViewModel

/**
 * A fragment representing subway bounds.
 */
class SubwayBoundsFragment : Fragment() {
    private lateinit var mSubwayBoundsViewModel: SubwayBoundsViewModel
    private var mListener: OnSubwayBoundsFragmentInteractionListener? = null
    private var mSubwayBoundsAdapter: SubwayBoundsRecyclerViewAdapter? = null

    companion object {
        const val TAG = "subway_bounds_fragment"
        private const val ARG_SUBWAY_STATION = "subway-station"
        private const val ARG_SUBWAY_SERVICE = "subway-service"

        fun newInstance(subwayStation: SubwayStation, subwayService: SubwayService): SubwayBoundsFragment {
            val fragment = SubwayBoundsFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_STATION, subwayStation)
            args.putSerializable(ARG_SUBWAY_SERVICE, subwayService)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            val subwayStation = arguments!![ARG_SUBWAY_STATION] as SubwayStation
            val subwayService = arguments!![ARG_SUBWAY_SERVICE] as SubwayService
            mSubwayBoundsViewModel = ViewModelProviders.of(this).get(SubwayBoundsViewModel::class.java)
            mSubwayBoundsViewModel.getSubwayBounds()?.observe(this, Observer { subwayBounds ->
                if(subwayBounds !== null) {
                    mSubwayBoundsAdapter?.mSubwayStation = subwayStation
                    mSubwayBoundsAdapter?.mSubwayService = subwayService
                    mSubwayBoundsAdapter?.mValues = subwayBounds
                    mSubwayBoundsAdapter?.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_bounds, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)

            mSubwayBoundsAdapter = SubwayBoundsRecyclerViewAdapter(mListener)
            view.adapter = mSubwayBoundsAdapter

            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayBoundsFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayBoundsFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayBoundsFragmentInteractionListener {
        fun onSubwayBoundClick(subwayStation: SubwayStation,
                               subwayService: SubwayService,
                               subwayBound: SubwayBound)
    }
}
