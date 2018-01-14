package com.shahzorequreshi.famta.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.fragments.*
import com.shahzorequreshi.famta.objects.Subway.SubwayStation
import com.shahzorequreshi.famta.objects.Subway.SubwayLine
import com.shahzorequreshi.famta.objects.Subway.SubwayService
import com.shahzorequreshi.famta.objects.Subway.SubwayBound
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Date

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
            R.id.navigation_subway_lines -> {
                changeFragment(SubwayFragment.newInstance())
            }
            R.id.navigation_feeds -> {
                changeFragment(FeedFragment.newInstance(1))
            }
            R.id.navigation_construction -> {
                changeFragment(ConstructionFragment.newInstance(1))
            }
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
        var fragment = supportFragmentManager.findFragmentById(chosenFragment.id)
        if(fragment == null) {
            fragment = chosenFragment
        }
        supportFragmentManager.beginTransaction().replace(fragmentContainer.id, fragment).commit()
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

    override fun onSubwayBoundFragmentInteraction(item: SubwayStation) {
        changeFragment(SubwayStationFragment.newInstance(item))
    }

    override fun onSubwayStationFragmentInteraction(item: Date) {
        //changeFragment(SubwayStationFragment.newInstance(item))
    }
}
