package com.shahzorequreshi.famta.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.database.entities.SubwayLineEvent
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayLineEventsRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayLineEventsViewModel
import java.io.Serializable

/**
 * A fragment representing subway line events.
 */
class SubwayLineEventsFragment : Fragment() {
    private lateinit var mSubwayLineEventsViewModel: SubwayLineEventsViewModel
    private var mSubwayLineEventsAdapter: SubwayLineEventsRecyclerViewAdapter? = null

    companion object {
        const val TAG = "subway_line_events_fragment"
        private const val ARG_SUBWAY_LINE_EVENTS = "subway-line-events"

        fun newInstance(subwayLineEvents: List<SubwayLineEvent>): SubwayLineEventsFragment {
            val fragment = SubwayLineEventsFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_LINE_EVENTS, subwayLineEvents as Serializable)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            @Suppress("UNCHECKED_CAST")
            val subwayLineEvents = arguments!![ARG_SUBWAY_LINE_EVENTS] as List<SubwayLineEvent>
            mSubwayLineEventsViewModel = ViewModelProviders
                    .of(this, SubwayLineEventsViewModel.Factory(subwayLineEvents))
                    .get(SubwayLineEventsViewModel::class.java)
            mSubwayLineEventsAdapter?.mValues = mSubwayLineEventsViewModel.getSubwayLineEvents()
            mSubwayLineEventsAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_line_events, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)

            mSubwayLineEventsAdapter = SubwayLineEventsRecyclerViewAdapter()
            view.adapter = mSubwayLineEventsAdapter

            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }
}
