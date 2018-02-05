package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.services.params.Geocode
import retrofit2.Call

/**
 * ViewModel that holds social feed information.
 */
class FeedsViewModel : ViewModel() {
    private var mTweetMediatorLiveData = MediatorLiveData<List<Tweet>>()
    private var mTweets = MutableLiveData<List<Tweet>>()
    private var mRequest: Call<Search>? = null

    init {
        mTweetMediatorLiveData.addSource(mTweets, {
            mTweetMediatorLiveData.postValue(it)
        })

        updateTweets()
    }

    fun getTweets(): LiveData<List<Tweet>> {
        return mTweetMediatorLiveData
    }

    fun updateTweets() {
        mRequest = TwitterCore.getInstance().apiClient.searchService.tweets(
                "mta delay -maryland", null, null, null, "recent",
                null, null, null, null, null)

        mRequest?.enqueue(object: Callback<Search>() {
            override fun success(result: Result<Search>?) {
                if (result?.data != null) {
                    mTweets.postValue(result.data.tweets)
                }
            }

            override fun failure(exception: TwitterException?) {
                println(exception?.localizedMessage)
            }
        })
    }
}