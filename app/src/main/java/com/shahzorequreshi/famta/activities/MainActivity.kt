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
        SubwayFragment.OnSubwayFragmentInteractionListener,
        SubwayServicesFragment.OnSubwayServicesFragmentInteractionListener,
        SubwayServiceFragment.OnSubwayServiceFragmentInteractionListener,
        SubwayBoundFragment.OnSubwayBoundFragmentInteractionListener,
        SubwayStationsFragment.OnSubwayStationsFragmentInteractionListener {

    @Inject lateinit var mRepo: SubwayRepository

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_subway_lines -> changeFragment(SubwayStationsFragment.newInstance())
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
        changeFragment(SubwayStationsFragment.newInstance())
    }

    private fun changeFragment(chosenFragment: Fragment) {
        var fragment = supportFragmentManager.findFragmentByTag(chosenFragment.tag)
        if(fragment == null) {
            fragment = chosenFragment
        }
        supportFragmentManager.beginTransaction().replace(fragmentContainer.id, fragment, fragment.tag).commit()
    }

    override fun onSubwayStationClick(subwayStation: SubwayStation) {
        changeFragment(SubwayServicesFragment.newInstance(subwayStation.service_ids))
    }

    override fun onConstructionFragmentInteraction(item: SubwayLine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFeedFragmentInteraction(item: SubwayLine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubwayFragmentInteraction(item: SubwayLine) {
        //changeFragment(SubwayServicesFragment.newInstance(item))
    }

    override fun onSubwayLineFragmentInteraction(item: SubwayService) {
        //changeFragment(SubwayServiceFragment.newInstance(item))
    }

    override fun onSubwayServiceFragmentInteraction(item: SubwayBound) {
        //changeFragment(SubwayBoundFragment.newInstance(item))
    }

    override fun onSubwayBoundFragmentInteraction(item: SubwayStation, item2: SubwayBound) {
        //changeFragment(SubwayStationsFragment.newInstance())
    }

//    override fun onSubwayTimeExpired(item: SubwayTime) {
//        mRepo.removeSubwayTime(item)
//    }
}
