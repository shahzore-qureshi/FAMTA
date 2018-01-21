package com.shahzorequreshi.famta.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.fragments.adapters.SubwayRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayViewModel

/**
 * A fragment representing a subway's list of subway lines.
 */
class SubwayFragment : Fragment() {
    private lateinit var mSubwayViewModel: SubwayViewModel
    private var mListener: OnSubwayFragmentInteractionListener? = null
    private var mSubwayAdapter: SubwayRecyclerViewAdapter? = null

    companion object {
        fun newInstance(): SubwayFragment {
            return SubwayFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSubwayViewModel = ViewModelProviders
                .of(this, SubwayViewModel.Factory())
                .get(SubwayViewModel::class.java)
        mSubwayViewModel.getSubwayLines()?.observe(this, Observer { subwayLines ->
            if(subwayLines !== null) {
                mSubwayAdapter?.mValues = subwayLines
                mSubwayAdapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)

            mSubwayAdapter = SubwayRecyclerViewAdapter(listOf(), mListener, activity)
            view.adapter = mSubwayAdapter

            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayFragmentInteractionListener {
        fun onSubwayFragmentInteraction(item: SubwayLine)
    }
}
