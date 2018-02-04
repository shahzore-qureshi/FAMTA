package com.shahzorequreshi.famta.recyclerviewadapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.shahzorequreshi.famta.R.layout.*
import com.shahzorequreshi.famta.fragments.FeedsFragment.OnFeedsFragmentInteractionListener
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetView

/**
 * [RecyclerView.Adapter] that can display social feed items.
 */
class FeedsRecyclerViewAdapter(
        private val mListener: OnFeedsFragmentInteractionListener?)
    : RecyclerView.Adapter<FeedsRecyclerViewAdapter.ViewHolder>() {

    var mValues = listOf<Tweet>()
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_feeds_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTweet = mValues[position]
        holder.mTweetLayout.addView(TweetView(mContext, holder.mTweet))
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mTweetLayout = mView as FrameLayout
        var mTweet: Tweet? = null
    }
}