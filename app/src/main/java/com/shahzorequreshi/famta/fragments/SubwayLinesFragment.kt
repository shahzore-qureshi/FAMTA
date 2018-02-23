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
import android.widget.ProgressBar
import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayLinesRecyclerViewAdapter
import com.shahzorequreshi.famta.recyclerviewadapters.SubwayStationsRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayLinesViewModel
import com.shahzorequreshi.famta.viewmodels.SubwayStationsViewModel

/**
 * A fragment representing subway lines.
 */
class SubwayLinesFragment : Fragment() {
    private lateinit var mSubwayStationsViewModel: SubwayLinesViewModel
    private var mListener: OnSubwayLinesFragmentInteractionListener? = null
    private var mSubwayStationsAdapter: SubwayLinesRecyclerViewAdapter? = null
    private var mLoadingView: ProgressBar? = null
    private var mRecyclerView: RecyclerView? = null
    private var mRecyclerViewState: Bundle? = null
    private val mRecyclerViewStateKey = "RECYCLER-VIEW"

    companion object {
        const val TAG = "subway_lines_fragment"
        fun newInstance() = SubwayLinesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_lines, container, false)
        val checkForLoadingView = view.findViewById<ProgressBar>(R.id.fragment_subway_lines_loading)
        if(checkForLoadingView is ProgressBar) {
            mLoadingView = checkForLoadingView
        }

        val checkForRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_subway_lines_recyclerview)
        if (checkForRecyclerView is RecyclerView) {
            val context = checkForRecyclerView.context
            checkForRecyclerView.layoutManager = LinearLayoutManager(context)
            if(mSubwayStationsAdapter == null) {
                mSubwayStationsAdapter = SubwayLinesRecyclerViewAdapter(mListener, context)
            }

            checkForRecyclerView.adapter = mSubwayStationsAdapter
            checkForRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            mRecyclerView = checkForRecyclerView
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSubwayStationsViewModel = ViewModelProviders.of(this).get(SubwayLinesViewModel::class.java)
        mSubwayStationsViewModel.getSubwayLines()?.observe(this, Observer { subwayLines ->
            if(subwayLines !== null) {
                mSubwayStationsAdapter?.mValues = subwayLines
                mSubwayStationsAdapter?.notifyDataSetChanged()
                mLoadingView?.visibility = View.GONE
                mRecyclerView?.visibility = View.VISIBLE
            }
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayLinesFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayLinesFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onResume() {
        super.onResume()
        if(mRecyclerViewState != null) {
            val savedState = mRecyclerViewState!!.getParcelable<Parcelable>(mRecyclerViewStateKey)
            mRecyclerView?.layoutManager?.onRestoreInstanceState(savedState)
        }
    }

    override fun onPause() {
        super.onPause()
        mRecyclerViewState = Bundle()
        mRecyclerViewState!!.putParcelable(mRecyclerViewStateKey, mRecyclerView?.layoutManager?.onSaveInstanceState())
    }

    interface OnSubwayLinesFragmentInteractionListener {
        fun onSubwayLineClick(subwayLine: SubwayLine)
    }
}
