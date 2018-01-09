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
import com.shahzorequreshi.famta.fragments.recycler_view_adapters.MySubwayLineDetailsRecyclerViewAdapter
import com.shahzorequreshi.famta.objects.Subway
import com.shahzorequreshi.famta.objects.Subway.SubwayLine
import com.shahzorequreshi.famta.objects.Subway.SubwayService

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class SubwayLineDetailsFragment : Fragment() {
    private var mSubwayLine: SubwayLine? = null
    private var mListener: OnSubwayLineDetailsFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mSubwayLine = Subway.Lines[arguments.getString(ARG_SUBWAY_LINE)]
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_subway_line_details, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            view.adapter = MySubwayLineDetailsRecyclerViewAdapter(mSubwayLine!!.services, mListener)
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayLineDetailsFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayLineDetailsFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayLineDetailsFragmentInteractionListener {
        fun onSubwayLineDetailsFragmentInteraction(item: SubwayService)
    }

    companion object {
        private val ARG_SUBWAY_LINE = "subway-line"

        fun newInstance(subwayLine: SubwayLine): SubwayLineDetailsFragment {
            val fragment = SubwayLineDetailsFragment()
            val args = Bundle()
            args.putString(ARG_SUBWAY_LINE, subwayLine.name)
            fragment.arguments = args
            return fragment
        }
    }
}
