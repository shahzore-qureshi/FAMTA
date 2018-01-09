package com.shahzorequreshi.famta.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.objects.Subway
import com.shahzorequreshi.famta.objects.Subway.SubwayLine

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class SubwayLineFragment : Fragment() {
    private var mListener: OnSubwayLineFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_subway_line, container, false)

        view.findViewById<LinearLayout>(R.id.subway_line_blue).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["blue"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_orange).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["orange"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_lime_green).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["lime green"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_light_gray).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["light gray"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_brown).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["brown"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_yellow).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["yellow"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_red).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["red"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_green).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["green"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_raspberry).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["raspberry"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_gray).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["gray"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_dark_blue).setOnClickListener {
            mListener?.onSubwayLineFragmentInteraction(Subway.Lines["dark blue"]!!)
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayLineFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFeedFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayLineFragmentInteractionListener {
        fun onSubwayLineFragmentInteraction(item: SubwayLine)
    }

    companion object {
        fun newInstance() = SubwayLineFragment()
    }
}
