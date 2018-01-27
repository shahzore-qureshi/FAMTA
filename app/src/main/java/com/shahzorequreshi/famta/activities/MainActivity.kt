package com.shahzorequreshi.famta.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.database.entities.*
import com.shahzorequreshi.famta.fragments.*
import com.shahzorequreshi.famta.repositories.SubwayRepository
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
        ConstructionFragment.OnConstructionFragmentInteractionListener,
        FeedFragment.OnFeedFragmentInteractionListener,
        SubwayServicesFragment.OnSubwayServicesFragmentInteractionListener,
        SubwayBoundsFragment.OnSubwayBoundsFragmentInteractionListener,
        SubwayStationsFragment.OnSubwayStationsFragmentInteractionListener,
        SubwayTimesFragment.OnSubwayTimesFragmentInteractionListener {

    @Inject lateinit var mRepo: SubwayRepository

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_subway_lines -> changeFragment(SubwayStationsFragment.newInstance(), SubwayStationsFragment.TAG)
            //R.id.navigation_feeds -> changeFragment(FeedFragment.newInstance(1))
            //R.id.navigation_construction -> changeFragment(ConstructionFragment.newInstance(1))
        }
        return@OnNavigationItemSelectedListener true
    }

    init {
        MainApplication.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        changeFragmentForFirstTime(SubwayStationsFragment.newInstance(), SubwayStationsFragment.TAG)
    }

    private fun changeFragmentForFirstTime(chosenFragment: Fragment, fragmentTag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(fragmentContainer.id, chosenFragment, fragmentTag)
        transaction.commit()
    }

    private fun changeFragment(chosenFragment: Fragment, fragmentTag: String) {
        var fragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        if(fragment == null) {
            fragment = chosenFragment
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(fragmentContainer.id, fragment, fragmentTag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onSubwayStationClick(subwayStation: SubwayStation) {
        changeFragment(SubwayServicesFragment.newInstance(subwayStation), SubwayServicesFragment.TAG)
    }

    override fun onSubwayServiceClick(subwayStation: SubwayStation, subwayService: SubwayService) {
        changeFragment(SubwayBoundsFragment.newInstance(subwayStation, subwayService), SubwayBoundsFragment.TAG)
    }

    override fun onSubwayBoundClick(subwayStation: SubwayStation, subwayService: SubwayService, subwayBound: SubwayBound) {
        changeFragment(SubwayTimesFragment.newInstance(subwayStation, subwayService, subwayBound), SubwayTimesFragment.TAG)
    }

    override fun onSubwayTimeExpired(subwayTime: SubwayTime) {
        //mRepo.removeSubwayTime(subwayTime)
    }

    override fun onSubwayTimeClick(subwayTime: SubwayTime) {
        //mRepo.removeSubwayTime(item)
    }

    override fun onConstructionFragmentInteraction(item: SubwayLine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFeedFragmentInteraction(item: SubwayLine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
