package com.shahzorequreshi.famta.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.recyclerviewadapters.FeedsRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.FeedsViewModel
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetcomposer.ComposerActivity
import com.twitter.sdk.android.tweetcomposer.TweetUploadService
import com.twitter.sdk.android.tweetui.TweetUtils

/**
 * A fragment representing social feeds.
 */
class FeedsFragment : Fragment() {
    private lateinit var mFeedsViewModel: FeedsViewModel
    private var mListener: OnFeedsFragmentInteractionListener? = null
    private var mFeedsAdapter: FeedsRecyclerViewAdapter? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null

    companion object {
        const val TAG = "feeds-fragment"
        fun newInstance(): FeedsFragment {
            return FeedsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_feeds, container, false)
        if(view is SwipeRefreshLayout) {
            mSwipeRefreshLayout = view
            mSwipeRefreshLayout?.post({
                mSwipeRefreshLayout?.isRefreshing = true
            })
        }

        val checkRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_feeds_recyclerview)
        if(checkRecyclerView is RecyclerView) {
            mRecyclerView = checkRecyclerView
            mRecyclerView?.layoutManager = LinearLayoutManager(context)

            mFeedsAdapter = FeedsRecyclerViewAdapter(mListener)
            mFeedsAdapter?.mOnRefreshListener = object : FeedsRecyclerViewAdapter.OnRefreshListener {
                override fun onRefresh() {
                    mFeedsViewModel.updateOlderTweets()
                }
            }
            mRecyclerView?.adapter = mFeedsAdapter
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
                mSwipeRefreshLayout?.isRefreshing = false
            }
        })
        mSwipeRefreshLayout?.setOnRefreshListener {
            mFeedsViewModel.updateTweets()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.navigation_fragment_feeds, menu)
    }

    private var mStartTwitterLoginButton: TwitterLoginButton? = null

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.navigation_fragment_feeds_compose -> {
                val session = TwitterCore.getInstance().sessionManager.activeSession
                if(session != null) {
                    val intent = ComposerActivity.Builder(context)
                            .session(session)
                            .hashtags("famta")
                            .createIntent()
                    startActivity(intent)
                } else {
                    mStartTwitterLoginButton = TwitterLoginButton(context)
                    mStartTwitterLoginButton?.callback = object: Callback<TwitterSession>() {
                        override fun success(result: Result<TwitterSession>?) {
                            if(result != null) {
                                val intent = ComposerActivity.Builder(context)
                                        .session(result.data)
                                        .hashtags("famta")
                                        .createIntent()
                                startActivity(intent)
                            }
                        }
                        override fun failure(exception: TwitterException?) {
                            println(exception?.localizedMessage)
                        }
                    }
                    mStartTwitterLoginButton?.callOnClick()
                }
                true
            }
            R.id.navigation_fragment_feeds_refresh -> {
                mSwipeRefreshLayout?.isRefreshing = true
                mFeedsViewModel.updateTweets()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mStartTwitterLoginButton?.onActivityResult(requestCode, resultCode, data)
    }

    private val mComposeTweetIntentFilter = IntentFilter()

    private val mComposeTweetReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                "com.twitter.sdk.android.tweetcomposer.UPLOAD_SUCCESS" -> {
                    val tweetId = intent.extras.getLong(TweetUploadService.EXTRA_TWEET_ID)
                    TweetUtils.loadTweet(tweetId, object: Callback<Tweet>() {
                        override fun success(result: Result<Tweet>?) {
                            val newList = mFeedsAdapter?.mValues?.toMutableList()
                            if(newList != null && result != null) {
                                newList.add(0, result.data)
                                mFeedsAdapter?.mValues = newList.toList()
                                mFeedsAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun failure(exception: TwitterException?) {
                            //todo
                        }
                    })
                }
                "com.twitter.sdk.android.tweetcomposer.UPLOAD_FAILURE" -> {
                    println("Twitter Upload Failure")
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFeedsFragmentInteractionListener) {
            mListener = context
            mComposeTweetIntentFilter.addAction("com.twitter.sdk.android.tweetcomposer.UPLOAD_SUCCESS")
            mComposeTweetIntentFilter.addAction("com.twitter.sdk.android.tweetcomposer.UPLOAD_FAILURE")
            activity?.registerReceiver(mComposeTweetReceiver, mComposeTweetIntentFilter)
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFeedsFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        activity?.unregisterReceiver(mComposeTweetReceiver)
    }

    interface OnFeedsFragmentInteractionListener {
        fun onFeedClick()
    }
}