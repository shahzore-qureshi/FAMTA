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
    private var mTweets = MutableLiveData<List<Tweet>>()

    init {
        updateTweets()
    }

    fun getTweets(): LiveData<List<Tweet>> {
        return mTweets
    }

    fun updateTweets() {
        val request = TwitterCore.getInstance().apiClient.searchService.tweets(
                "mta delay -maryland", null, null, null, "mixed",
                15, null, mTweets.value?.first()?.id, null, null)

        request.enqueue(object: Callback<Search>() {
            override fun success(result: Result<Search>?) {
                if (result?.data != null) {
                    var newList = mTweets.value?.toMutableList()
                    if(newList == null) newList = mutableListOf()
                    newList.addAll(0, result.data.tweets)
                    mTweets.postValue(newList)
                }
            }

            override fun failure(exception: TwitterException?) {
                println(exception?.localizedMessage)
            }
        })
    }

    fun updateOlderTweets() {
        val request = TwitterCore.getInstance().apiClient.searchService.tweets(
                "mta delay -maryland", null, null, null, "mixed",
                15, null, null, mTweets.value?.last()?.id, null)

        request.enqueue(object: Callback<Search>() {
            override fun success(result: Result<Search>?) {
                if (result?.data != null) {
                    var newList = mTweets.value?.toMutableList()
                    if(newList == null) newList = mutableListOf()
                    newList.addAll(result.data.tweets)
                    mTweets.postValue(newList)
                }
            }

            override fun failure(exception: TwitterException?) {
                println(exception?.localizedMessage)
            }
        })
    }
}