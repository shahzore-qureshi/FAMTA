package com.shahzorequreshi.famta.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.database.entities.SubwayService
import android.support.v7.widget.DividerItemDecoration
import com.shahzorequreshi.famta.fragments.adapters.SubwayServicesRecyclerViewAdapter
import com.shahzorequreshi.famta.viewmodels.SubwayServicesViewModel
import java.io.Serializable

/**
 * A fragment representing subway services.
 */
class SubwayServicesFragment : Fragment() {
    private lateinit var mSubwayServicesViewModel: SubwayServicesViewModel
    private var mListener: OnSubwayServicesFragmentInteractionListener? = null
    private var mSubwayServicesAdapter: SubwayServicesRecyclerViewAdapter? = null

    companion object {
        private const val ARG_SUBWAY_SERVICES = "subway-services"

        fun newInstance(subwayServices: List<String>): SubwayServicesFragment {
            val fragment = SubwayServicesFragment()
            val args = Bundle()
            args.putSerializable(ARG_SUBWAY_SERVICES, subwayServices as Serializable)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments is Bundle) {
            @Suppress("UNCHECKED_CAST")
            val subwayServices = (arguments as Bundle).getSerializable(ARG_SUBWAY_SERVICES) as List<String>
            mSubwayServicesViewModel = ViewModelProviders
                    .of(this, SubwayServicesViewModel.Factory(subwayServices))
                    .get(SubwayServicesViewModel::class.java)
            mSubwayServicesViewModel.getSubwayServices()?.observe(this, Observer { services ->
                if(services !== null) {
                    mSubwayServicesAdapter?.mValues = services
                    mSubwayServicesAdapter?.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subway_services, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)

            mSubwayServicesAdapter = SubwayServicesRecyclerViewAdapter(listOf(), mListener, activity)
            view.adapter = mSubwayServicesAdapter

            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayServicesFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayServicesFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayServicesFragmentInteractionListener {
        fun onSubwayLineFragmentInteraction(item: SubwayService)
    }
}
