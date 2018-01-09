package com.shahzorequreshi.famta.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.fragments.ConstructionFragment
import com.shahzorequreshi.famta.fragments.FeedFragment
import com.shahzorequreshi.famta.fragments.SubwayLineDetailsFragment
import com.shahzorequreshi.famta.fragments.SubwayLineFragment
import com.shahzorequreshi.famta.objects.Subway.SubwayLine
import com.shahzorequreshi.famta.objects.Subway.SubwayService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        ConstructionFragment.OnConstructionFragmentInteractionListener,
        FeedFragment.OnFeedFragmentInteractionListener,
        SubwayLineFragment.OnSubwayLineFragmentInteractionListener,
        SubwayLineDetailsFragment.OnSubwayLineDetailsFragmentInteractionListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_subway_lines -> {
                changeFragment(SubwayLineFragment.newInstance())
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
        changeFragment(SubwayLineFragment.newInstance())
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

    override fun onSubwayLineFragmentInteraction(item: SubwayLine) {
        changeFragment(SubwayLineDetailsFragment.newInstance(item))
    }

    override fun onSubwayLineDetailsFragmentInteraction(item: SubwayService) {
        //changeFragment(SubwayServiceFragment.newInstance(item))
    }
}
