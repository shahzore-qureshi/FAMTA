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
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.shahzorequreshi.famta.recyclerviewadapters.FeedsRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.FeedsViewModel

/**
 * A fragment representing social feeds.
 */
class FeedsFragment : Fragment() {
    private lateinit var mFeedsViewModel: FeedsViewModel
    private var mListener: OnFeedsFragmentInteractionListener? = null
    private var mFeedsAdapter: FeedsRecyclerViewAdapter? = null
    private var mLoadingView: ProgressBar? = null
    private var mRecyclerView: RecyclerView? = null

    companion object {
        const val TAG = "feeds-fragment"
        fun newInstance(): FeedsFragment {
            return FeedsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_feeds, container, false)

        val checkLoadingView = view.findViewById<ProgressBar>(R.id.fragment_feeds_loading)
        if(checkLoadingView is ProgressBar) {
            mLoadingView = checkLoadingView
        }

        val checkRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_feeds_recyclerview)
        if(checkRecyclerView is RecyclerView) {
            checkRecyclerView.layoutManager = LinearLayoutManager(context)

            mFeedsAdapter = FeedsRecyclerViewAdapter(mListener)
            checkRecyclerView.adapter = mFeedsAdapter

            mRecyclerView = checkRecyclerView
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mFeedsViewModel = ViewModelProviders.of(this).get(FeedsViewModel::class.java)
        mFeedsViewModel.getTweets().observe(this, Observer {
            if(it != null) {
                mFeedsAdapter?.mValues = it
                mFeedsAdapter?.notifyDataSetChanged()
                mLoadingView?.visibility = View.GONE
                mRecyclerView?.visibility = View.VISIBLE
            }
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFeedsFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFeedsFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFeedsFragmentInteractionListener {
        fun onFeedClick()
    }
}
