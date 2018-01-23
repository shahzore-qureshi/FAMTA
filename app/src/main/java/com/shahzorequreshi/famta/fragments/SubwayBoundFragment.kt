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
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.fragments.adapters.SubwayBoundRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayBoundViewModel

/**
 * A fragment representing subway bound information.
 *
 */
class SubwayBoundFragment : Fragment() {
    private lateinit var mSubwayBoundViewModel: SubwayBoundViewModel
    private var mListener: OnSubwayBoundFragmentInteractionListener? = null
    private var mSubwayBoundAdapter: SubwayBoundRecyclerViewAdapter? = null

    companion object {
        private const val ARG_SUBWAY_BOUND = "subway-bound"

        fun newInstance(subwayBound: SubwayBound): SubwayBoundFragment {
            val fragment = SubwayBoundFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_BOUND, subwayBound)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val subwayBound = arguments!![ARG_SUBWAY_BOUND] as SubwayBound
            mSubwayBoundViewModel = ViewModelProviders
                    .of(this, SubwayBoundViewModel.Factory(subwayBound))
                    .get(SubwayBoundViewModel::class.java)
            mSubwayBoundViewModel.getSubwayStations()?.observe(this, Observer { subwayStations ->
                if(subwayStations !== null) {
                    mSubwayBoundAdapter?.mSubwayBound = subwayBound
                    mSubwayBoundAdapter?.mValues = subwayStations
                    mSubwayBoundAdapter?.notifyDataSetChanged()
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

            mSubwayBoundAdapter = SubwayBoundRecyclerViewAdapter(listOf(), mListener, activity)
            view.adapter = mSubwayBoundAdapter

            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayBoundFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayBoundFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayBoundFragmentInteractionListener {
        fun onSubwayBoundFragmentInteraction(item: SubwayStation, item2: SubwayBound)
    }
}
