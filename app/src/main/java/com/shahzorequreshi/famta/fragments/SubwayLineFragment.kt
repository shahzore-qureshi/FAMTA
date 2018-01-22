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
import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.database.entities.SubwayService
import android.support.v7.widget.DividerItemDecoration
import com.shahzorequreshi.famta.fragments.adapters.SubwayLineRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayLineViewModel

/**
 * A fragment representing a subway line's list of subway services.
 */
class SubwayLineFragment : Fragment() {
    private lateinit var mSubwayLineViewModel: SubwayLineViewModel
    private var mListener: OnSubwayLineFragmentInteractionListener? = null
    private var mSubwayLineAdapter: SubwayLineRecyclerViewAdapter? = null

    companion object {
        private const val ARG_SUBWAY_LINE = "subway-line"

        fun newInstance(subwayLine: SubwayLine): SubwayLineFragment {
            val fragment = SubwayLineFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_LINE, subwayLine)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val subwayLine = arguments!!.getSerializable(ARG_SUBWAY_LINE) as SubwayLine
            mSubwayLineViewModel = ViewModelProviders
                    .of(this, SubwayLineViewModel.Factory(subwayLine))
                    .get(SubwayLineViewModel::class.java)
            mSubwayLineViewModel.getSubwayServices()?.observe(this, Observer { subwayServices ->
                if(subwayServices !== null) {
                    mSubwayLineAdapter?.mValues = subwayServices
                    mSubwayLineAdapter?.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_line, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)

            mSubwayLineAdapter = SubwayLineRecyclerViewAdapter(listOf(), mListener, activity)
            view.adapter = mSubwayLineAdapter

            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayLineFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayLineFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayLineFragmentInteractionListener {
        fun onSubwayLineFragmentInteraction(item: SubwayService)
    }
}
