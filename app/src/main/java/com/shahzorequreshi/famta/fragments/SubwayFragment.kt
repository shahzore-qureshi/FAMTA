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
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class SubwayFragment : Fragment() {
    private var mListener: OnSubwayFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_subway, container, false)

        view.findViewById<LinearLayout>(R.id.subway_line_blue).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["blue"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_orange).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["orange"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_lime_green).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["lime green"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_light_gray).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["light gray"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_brown).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["brown"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_yellow).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["yellow"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_red).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["red"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_green).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["green"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_raspberry).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["raspberry"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_gray_manhattan).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["gray manhattan"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_gray_queens).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["gray queens"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_gray_brooklyn).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["gray brooklyn"]!!)
        }

        view.findViewById<LinearLayout>(R.id.subway_line_dark_blue).setOnClickListener {
            mListener?.onSubwayFragmentInteraction(Subway.Lines["dark blue"]!!)
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubwayFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnSubwayFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnSubwayFragmentInteractionListener {
        fun onSubwayFragmentInteraction(item: SubwayLine)
    }

    companion object {
        fun newInstance() = SubwayFragment()
    }
}
