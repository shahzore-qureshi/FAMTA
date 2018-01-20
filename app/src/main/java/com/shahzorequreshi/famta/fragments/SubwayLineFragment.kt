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
import com.shahzorequreshi.famta.fragments.adapters.SubwayLineRecyclerViewAdapter
import com.shahzorequreshi.famta.database.objects.SubwayLine
import com.shahzorequreshi.famta.database.objects.SubwayService
import android.support.v7.widget.DividerItemDecoration

/**
 * A fragment representing a subway line's list of subway services.
 */
class SubwayLineFragment : Fragment() {
    private var mSubwayLine: SubwayLine? = null
    private var mListener: OnSubwayLineFragmentInteractionListener? = null

    companion object {
        private val ARG_SUBWAY_LINE = "subway-line"

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
            mSubwayLine = arguments!!.getSerializable(ARG_SUBWAY_LINE) as SubwayLine
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_line, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            //view.adapter = SubwayLineRecyclerViewAdapter(mSubwayLine!!.services, mListener, activity)
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
