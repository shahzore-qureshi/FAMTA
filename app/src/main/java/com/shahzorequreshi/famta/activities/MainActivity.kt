package com.shahzorequreshi.famta.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.FragmentManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.database.entities.*
import com.shahzorequreshi.famta.fragments.*
import com.shahzorequreshi.famta.repositories.SubwayRepository
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient

class MainActivity : AppCompatActivity(),
        ConstructionFragment.OnConstructionFragmentInteractionListener,
        FeedFragment.OnFeedFragmentInteractionListener,
        SubwayServicesFragment.OnSubwayServicesFragmentInteractionListener,
        SubwayBoundsFragment.OnSubwayBoundsFragmentInteractionListener,
        SubwayStationsFragment.OnSubwayStationsFragmentInteractionListener,
        SubwayTimesFragment.OnSubwayTimesFragmentInteractionListener,
        LocationRequestDialogFragment.OnLocationRequestDialogFragmentInteractionListener {

    @Inject lateinit var mRepo: SubwayRepository
    @Inject lateinit var mLocationProvider: FusedLocationProviderClient
    private val mRequestForLocationPermission = 1
    private lateinit var mLayout: View
    private val mBackStackRootTag = "root-fragment"

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_subway_lines -> changeFragmentWithoutHistory(SubwayStationsFragment.newInstance(), SubwayStationsFragment.TAG)
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
        mLayout = findViewById(R.id.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        changeFragmentWithoutHistory(SubwayStationsFragment.newInstance(), SubwayStationsFragment.TAG)
    }

    private fun changeFragmentWithoutHistory(chosenFragment: Fragment, fragmentTag: String) {
        supportFragmentManager
                .popBackStack(mBackStackRootTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
                .beginTransaction()
                .replace(fragmentContainer.id, chosenFragment, fragmentTag)
                .commit()
    }

    private fun changeFragment(chosenFragment: Fragment, fragmentTag: String, backStackTag: String? = null) {
        var fragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        if(fragment == null) {
            fragment = chosenFragment
        }
        supportFragmentManager
                .beginTransaction()
                .replace(fragmentContainer.id, fragment, fragmentTag)
                .addToBackStack(backStackTag)
                .commit()
    }

    override fun onSubwayStationClick(subwayStation: SubwayStation) {
        changeFragment(SubwayServicesFragment.newInstance(subwayStation), SubwayServicesFragment.TAG, mBackStackRootTag)
    }

    override fun onLocationRequest() {
        requestLocationPermission()
    }

    override fun onSubwayServiceClick(subwayStation: SubwayStation, subwayService: SubwayService) {
        changeFragment(SubwayBoundsFragment.newInstance(subwayStation, subwayService), SubwayBoundsFragment.TAG)
    }

    override fun onSubwayBoundClick(subwayStation: SubwayStation, subwayService: SubwayService, subwayBound: SubwayBound) {
        changeFragment(SubwayTimesFragment.newInstance(subwayStation, subwayService, subwayBound), SubwayTimesFragment.TAG)
    }

    override fun onSubwayTimeExpired(subwayTime: SubwayTime) {
        mRepo.removeSubwayTime(subwayTime)
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

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                LocationRequestDialogFragment().show(supportFragmentManager, null)
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        mRequestForLocationPermission)
            }
        } else {
            initializeLocator()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if(requestCode == mRequestForLocationPermission) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                initializeLocator()
            } else {
                mRepo.setUserLocation(null, null)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initializeLocator() {
        mLocationProvider.lastLocation.addOnCompleteListener {
            if(it.isSuccessful) {
                mRepo.setUserLocation(it.result.latitude, it.result.longitude)
            }
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                mRequestForLocationPermission)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        mRepo.setUserLocation(null, null)
    }
}
