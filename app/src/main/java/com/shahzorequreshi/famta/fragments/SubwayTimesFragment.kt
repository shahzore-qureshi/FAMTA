package com.shahzorequreshi.famta.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shahzorequreshi.famta.R
import android.support.v7.widget.DividerItemDecoration
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.database.entities.SubwayService
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.database.entities.SubwayTime
import com.shahzorequreshi.famta.fragments.adapters.SubwayTimesRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayTimesViewModel

/**
 * A fragment representing subway times and ETAs.
 */
class SubwayTimesFragment : Fragment() {
    private lateinit var mSubwayTimesViewModel: SubwayTimesViewModel
    private var mListener: OnSubwayTimesFragmentInteractionListener? = null
    private var mSubwayTimesAdapter: SubwayTimesRecyclerViewAdapter? = null

    companion object {
        const val TAG = "subway_times_fragment"
        private const val ARG_SUBWAY_STATION = "subway-station"
        private const val ARG_SUBWAY_SERVICE = "subway-service"
        private const val ARG_SUBWAY_BOUND = "subway-bound"

        fun newInstance(subwayStation: SubwayStation,
                        subwayService: SubwayService,
                        subwayBound: SubwayBound): SubwayTimesFragment {
            val fragment = SubwayTimesFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_STATION, subwayStation)
            args.putSerializable(ARG_SUBWAY_SERVICE, subwayService)
            args.putSerializable(ARG_SUBWAY_BOUND, subwayBound)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            val subwayStation = arguments!![ARG_SUBWAY_STATION] as SubwayStation
            val subwayService = arguments!![ARG_SUBWAY_SERVICE] as SubwayService
            val subwayBound = arguments!![ARG_SUBWAY_BOUND] as SubwayBound

            mSubwayTimesViewModel = ViewModelProviders
                    .of(this, SubwayTimesViewModel.Factory(subwayStation, subwayService, subwayBound))
                    .get(SubwayTimesViewModel::class.java)
            mSubwayTimesViewModel = ViewModelProviders.of(this).get(SubwayTimesViewModel::class.java)
            mSubwayTimesViewModel.getSubwayTimes()?.observe(this, Observer { subwayTimes ->
                if(subwayTimes !== null) {
                    if(subwayTimes.isEmpty() && view != null) {
                        Snackbar.make(view!!, getText(R.string.trains_not_available), Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.reluctant_ok, {

                                }).show()
                    }
                    mSubwayTimesAdapter?.mValues = subwayTimes
                    mSubwayTimesAdapter?.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_times, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)

            mSubwayTimesAdapter = SubwayTimesRecyclerViewAdapter(mListener)
            view.adapter = mSubwayTimesAdapter

            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayTimesFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayTimesFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayTimesFragmentInteractionListener {
        fun onSubwayTimeClick(subwayTime: SubwayTime)
        fun onSubwayTimeExpired(subwayTime: SubwayTime)
    }
}
