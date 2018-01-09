package com.shahzorequreshi.famta.fragments.recycler_view_adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shahzorequreshi.famta.R.id.construction_list_item_text
import com.shahzorequreshi.famta.R.layout.fragment_construction_list_item

import com.shahzorequreshi.famta.fragments.ConstructionFragment.OnConstructionFragmentInteractionListener
import com.shahzorequreshi.famta.objects.Subway.SubwayLine

/**
 * [RecyclerView.Adapter] that can display a [SubwayLine] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyConstructionRecyclerViewAdapter(
        private val mValues: List<SubwayLine>,
        private val mListener: OnConstructionFragmentInteractionListener?
) : RecyclerView.Adapter<MyConstructionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(fragment_construction_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues.get(position)
        //holder.mIdView.setText(mValues.get(position).id)

        holder.mView.setOnClickListener {
            mListener?.onConstructionFragmentInteraction(holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.findViewById(construction_list_item_text) as TextView
        var mItem: SubwayLine? = null

        override fun toString(): String {
            return "${super.toString()} '${mIdView.text}'"
        }
    }
}
