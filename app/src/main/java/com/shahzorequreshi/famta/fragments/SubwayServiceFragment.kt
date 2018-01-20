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
import com.shahzorequreshi.famta.database.objects.SubwayService
import com.shahzorequreshi.famta.database.objects.SubwayBound
import android.support.v7.widget.DividerItemDecoration
import com.shahzorequreshi.famta.fragments.adapters.SubwayServiceRecyclerViewAdapter

/**
 * A fragment representing subway service information.
 */
class SubwayServiceFragment : Fragment() {
    private var mSubwayService: SubwayService? = null
    private var mListener: OnSubwayServiceFragmentInteractionListener? = null

    companion object {
        private val ARG_SUBWAY_SERVICE = "subway-service"

        fun newInstance(subwayService: SubwayService): SubwayServiceFragment {
            val fragment = SubwayServiceFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_SERVICE, subwayService)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mSubwayService = arguments!![ARG_SUBWAY_SERVICE] as SubwayService
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_service, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            //view.adapter = SubwayServiceRecyclerViewAdapter(mSubwayService!!.bounds, mListener, activity)
            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayServiceFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayBoundFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayServiceFragmentInteractionListener {
        fun onSubwayServiceFragmentInteraction(item: SubwayBound)
    }
}
