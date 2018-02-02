package com.shahzorequreshi.famta.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.database.entities.SubwayService
import android.widget.ImageView
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.util.SubwayMaps
import com.shahzorequreshi.famta.viewmodels.SubwayServicesViewModel
import java.io.Serializable

/**
 * A fragment representing subway services.
 */
class SubwayServicesFragment : Fragment() {
    private var mListener: OnSubwayServicesFragmentInteractionListener? = null
    private lateinit var mSubwayServicesViewModel: SubwayServicesViewModel
    private var mViewLayout: Int = 0
    private lateinit var mView: View

    companion object {
        const val TAG = "subway_services_fragment"
        private var mServiceCount = 0
        private const val ARG_SUBWAY_SERVICES = "subway-services"

        fun newInstance(subwayServices: List<String>): SubwayServicesFragment {
            val fragment = SubwayServicesFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_SERVICES, subwayServices as Serializable)
            fragment.arguments = args
            mServiceCount = subwayServices.size
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayServicesFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayServicesFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mViewLayout = R.layout.fragment_subway_services_one
        when(mServiceCount) {
            1 -> mViewLayout = R.layout.fragment_subway_services_one
            2 -> mViewLayout = R.layout.fragment_subway_services_two
            3 -> mViewLayout = R.layout.fragment_subway_services_three
            4 -> mViewLayout = R.layout.fragment_subway_services_four
        }
        mView = inflater.inflate(mViewLayout, container, false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            @Suppress("UNCHECKED_CAST")
            val subwayServiceStrings = (arguments as Bundle).getSerializable(ARG_SUBWAY_SERVICES) as List<String>
            val subwayServices = subwayServiceStrings.map { SubwayService(it, it) }
            mSubwayServicesViewModel = ViewModelProviders
                    .of(this, SubwayServicesViewModel.Factory(subwayServices))
                    .get(SubwayServicesViewModel::class.java)
            initializeView(mView, mViewLayout)
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayServicesFragmentInteractionListener {
        fun onSubwayServiceClick(subwayStation: SubwayStation, subwayService: SubwayService)
    }

    private fun initializeView(view: View, viewLayout: Int) {
        when(viewLayout) {
            R.layout.fragment_subway_services_one -> {
                if(view is ImageView) {
                    view.setImageResource(
                            SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[0]))
                    ViewCompat.setTransitionName(view, "service-1")
                }
            }
            R.layout.fragment_subway_services_two -> {
                val imageView1 = view.findViewById<ImageView>(R.id.fragment_subway_services_two_first_service)
                val imageView2 = view.findViewById<ImageView>(R.id.fragment_subway_services_two_second_service)
                imageView1.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[0]))
                imageView2.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[1]))
                ViewCompat.setTransitionName(imageView1, "service-1")
                ViewCompat.setTransitionName(imageView2, "service-2")
            }
            R.layout.fragment_subway_services_three -> {
                val imageView1 = view.findViewById<ImageView>(R.id.fragment_subway_services_three_first_service)
                val imageView2 = view.findViewById<ImageView>(R.id.fragment_subway_services_three_second_service)
                val imageView3 = view.findViewById<ImageView>(R.id.fragment_subway_services_three_third_service)
                imageView1.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[0]))
                imageView2.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[1]))
                imageView3.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[2]))
                ViewCompat.setTransitionName(imageView1, "service-1")
                ViewCompat.setTransitionName(imageView2, "service-2")
                ViewCompat.setTransitionName(imageView3, "service-3")
            }
            R.layout.fragment_subway_services_four -> {
                val imageView1 = view.findViewById<ImageView>(R.id.fragment_subway_services_four_first_service)
                val imageView2 = view.findViewById<ImageView>(R.id.fragment_subway_services_four_second_service)
                val imageView3 = view.findViewById<ImageView>(R.id.fragment_subway_services_four_third_service)
                val imageView4 = view.findViewById<ImageView>(R.id.fragment_subway_services_four_fourth_service)
                imageView1.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[0]))
                imageView2.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[1]))
                imageView3.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[2]))
                imageView4.setImageResource(
                        SubwayMaps.getDrawableIdForSubwayService(mSubwayServicesViewModel.subwayServices[3]))
                ViewCompat.setTransitionName(imageView1, "service-1")
                ViewCompat.setTransitionName(imageView2, "service-2")
                ViewCompat.setTransitionName(imageView3, "service-3")
                ViewCompat.setTransitionName(imageView4, "service-4")
            }
        }
    }
}
