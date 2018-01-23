package com.shahzorequreshi.famta.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.database.entities.*
import com.shahzorequreshi.famta.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        ConstructionFragment.OnConstructionFragmentInteractionListener,
        FeedFragment.OnFeedFragmentInteractionListener,
        SubwayFragment.OnSubwayFragmentInteractionListener,
        SubwayLineFragment.OnSubwayLineFragmentInteractionListener,
        SubwayServiceFragment.OnSubwayServiceFragmentInteractionListener,
        SubwayBoundFragment.OnSubwayBoundFragmentInteractionListener,
        SubwayStationFragment.OnSubwayStationFragmentInteractionListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_subway_lines -> changeFragment(SubwayFragment.newInstance())
            //R.id.navigation_feeds -> changeFragment(FeedFragment.newInstance(1))
            //R.id.navigation_construction -> changeFragment(ConstructionFragment.newInstance(1))
        }
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        changeFragment(SubwayFragment.newInstance())
    }

    private fun changeFragment(chosenFragment: Fragment) {
        var fragment = supportFragmentManager.findFragmentByTag(chosenFragment.tag)
        if(fragment == null) {
            fragment = chosenFragment
        }
        supportFragmentManager.beginTransaction().replace(fragmentContainer.id, fragment, fragment.tag).commit()
    }

    override fun onConstructionFragmentInteraction(item: SubwayLine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFeedFragmentInteraction(item: SubwayLine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubwayFragmentInteraction(item: SubwayLine) {
        changeFragment(SubwayLineFragment.newInstance(item))
    }

    override fun onSubwayLineFragmentInteraction(item: SubwayService) {
        changeFragment(SubwayServiceFragment.newInstance(item))
    }

    override fun onSubwayServiceFragmentInteraction(item: SubwayBound) {
        changeFragment(SubwayBoundFragment.newInstance(item))
    }

    override fun onSubwayBoundFragmentInteraction(item: SubwayStation, item2: SubwayBound) {
        changeFragment(SubwayStationFragment.newInstance(item, item2))
    }

    override fun onSubwayStationFragmentInteraction(item: SubwayTime) {
        //changeFragment(SubwayStationFragment.newInstance(item))
    }
}
