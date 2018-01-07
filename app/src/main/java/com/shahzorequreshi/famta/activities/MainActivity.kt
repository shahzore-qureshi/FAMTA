package com.shahzorequreshi.famta.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.fragments.ConstructionFragment
import com.shahzorequreshi.famta.fragments.FeedFragment
import com.shahzorequreshi.famta.fragments.SubwayLineFragment
import com.shahzorequreshi.famta.fragments.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FeedFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: DummyContent.DummyItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mSubwayLineFragment: SubwayLineFragment? = null
    private var mFeedFragment: FeedFragment? = null
    private var mConstructionFragment: ConstructionFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.navigation_subway_lines -> {
                if(mSubwayLineFragment == null) mSubwayLineFragment = SubwayLineFragment.newInstance(1)
                selectedFragment = mSubwayLineFragment as SubwayLineFragment
            }
            R.id.navigation_feeds -> {
                if(mFeedFragment == null) mFeedFragment = FeedFragment.newInstance(1)
                selectedFragment = mFeedFragment as FeedFragment
            }
            R.id.navigation_construction -> {
                if(mConstructionFragment == null) mConstructionFragment = ConstructionFragment.newInstance(1)
                selectedFragment = mConstructionFragment as ConstructionFragment
            }
        }
        supportFragmentManager.beginTransaction().replace(fragmentContainer.id, selectedFragment).commit()
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
