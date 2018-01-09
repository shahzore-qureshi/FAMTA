package com.shahzorequreshi.famta.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.fragments.ConstructionFragment
import com.shahzorequreshi.famta.fragments.FeedFragment
import com.shahzorequreshi.famta.fragments.SubwayLineBlueFragment
import com.shahzorequreshi.famta.fragments.SubwayLineFragment
import com.shahzorequreshi.famta.objects.Subway
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        ConstructionFragment.OnConstructionFragmentInteractionListener,
        FeedFragment.OnFeedFragmentInteractionListener,
        SubwayLineFragment.OnSubwayLineFragmentInteractionListener,
        SubwayLineBlueFragment.OnSubwayLineBlueFragmentInteractionListener {

    private var mFeedFragment: FeedFragment? = null
    private var mConstructionFragment: ConstructionFragment? = null
    private enum class FragmentName

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_subway_lines -> {
                changeFragment(SubwayLineFragment.getInstance())
            }
            R.id.navigation_feeds -> {
                if(mFeedFragment == null) mFeedFragment = FeedFragment.newInstance(1)
                changeFragment(mFeedFragment!!)
            }
            R.id.navigation_construction -> {
                if(mConstructionFragment == null) mConstructionFragment = ConstructionFragment.newInstance(1)
                changeFragment(mConstructionFragment!!)
            }
        }
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        changeFragment(SubwayLineFragment.getInstance())
    }

    private fun changeFragment(chosenFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fragmentContainer.id, chosenFragment).commit()
    }

    override fun onConstructionFragmentInteraction(item: Subway.SubwayLine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFeedFragmentInteraction(item: Subway.SubwayLine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubwayLineFragmentInteraction(item: Subway.SubwayLine) {
        when(item.subwayLineName) {
            "blue" -> changeFragment(SubwayLineBlueFragment.getInstance())
        }
    }

    override fun onSubwayLineBlueFragmentInteraction(item: Subway.SubwayLine) {
        when(item.subwayLineName) {
            "blue" -> changeFragment(SubwayLineBlueFragment.getInstance())
        }
    }
}
