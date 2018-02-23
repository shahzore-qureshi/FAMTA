package com.shahzorequreshi.famta.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.FragmentManager
import android.content.Intent
import android.content.IntentSender
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
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity(),
        SubwayServicesFragment.OnSubwayServicesFragmentInteractionListener,
        SubwayBoundsFragment.OnSubwayBoundsFragmentInteractionListener,
        SubwayStationsFragment.OnSubwayStationsFragmentInteractionListener,
        SubwayTimesFragment.OnSubwayTimesFragmentInteractionListener,
        LocationRequestDialogFragment.OnLocationRequestDialogFragmentInteractionListener,
        FeedsFragment.OnFeedsFragmentInteractionListener,
        SubwayLinesFragment.OnSubwayLinesFragmentInteractionListener {

    @Inject lateinit var mRepo: SubwayRepository
    @Inject lateinit var mLocationProvider: FusedLocationProviderClient
    private val mRequestForLocationPermission = 1
    private val mBackStackRootTag = "root-fragment"

    init {
        MainApplication.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        changeFragmentWithoutHistory(SubwayStationsFragment.newInstance(), SubwayStationsFragment.TAG)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_subway_stations -> changeFragmentWithoutHistory(SubwayStationsFragment.newInstance(), SubwayStationsFragment.TAG)
            R.id.navigation_subway_lines -> changeFragmentWithoutHistory(SubwayLinesFragment.newInstance(), SubwayLinesFragment.TAG)
            R.id.navigation_feeds -> changeFragmentWithoutHistory(FeedsFragment.newInstance(), FeedsFragment.TAG)
        }
        return@OnNavigationItemSelectedListener true
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

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                LocationRequestDialogFragment().show(supportFragmentManager, null)
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
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
        val locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).build()
        LocationServices.getSettingsClient(this).checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener {
                    mLocationProvider.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
                }
                .addOnFailureListener {
                    if(it is ResolvableApiException) {
                        try {
                            it.startResolutionForResult(this, mRequestForLocationPermission)
                        } catch (err: IntentSender.SendIntentException) {

                        }
                    }
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == mRequestForLocationPermission) {
            initializeLocator()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
            supportFragmentManager.findFragmentByTag(FeedsFragment.TAG)?.onActivityResult(requestCode, resultCode, data)
        }
    }

    private val mLocationRequest = LocationRequest()
            .setInterval(10000)
            .setFastestInterval(5000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

    private val mLocationCallback = object: LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            if(result != null && result.locations.size > 0) {
                mRepo.setUserLocation(result.locations[0].latitude, result.locations[0].longitude)
                mLocationProvider.removeLocationUpdates(this)
            }
        }
    }

    private fun cancelLocationRequest() {
        mLocationProvider.removeLocationUpdates(mLocationCallback)
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                mRequestForLocationPermission)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        mRepo.setUserLocation(null, null)
    }

    override fun onSubwayStationClick(subwayStation: SubwayStation) {
        changeFragment(SubwayServicesFragment.newInstance(subwayStation), SubwayServicesFragment.TAG, mBackStackRootTag)
    }

    override fun onLocationRequest() {
        requestLocationPermission()
    }

    override fun onCancelLocationRequest() {
        cancelLocationRequest()
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

    override fun onFeedClick() {
        //todo
    }

    override fun onSubwayLineClick(subwayLine: SubwayLine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}