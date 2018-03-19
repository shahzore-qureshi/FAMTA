package com.shahzorequreshi.famta.recyclerviewadapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.R.layout.fragment_subway_line_events_list_item
import com.shahzorequreshi.famta.database.entities.SubwayLineEvent

/**
 * [RecyclerView.Adapter] that can display subway events.
 */
class SubwayLineEventsRecyclerViewAdapter()
    : RecyclerView.Adapter<SubwayLineEventsRecyclerViewAdapter.ViewHolder>() {

    var mValues = listOf<SubwayLineEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(fragment_subway_line_events_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mTextViewTitle.text = holder.mItem!!.title
        holder.mTextViewBody.text = holder.mItem!!.body
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextViewTitle: TextView = mView.findViewById(R.id.fragment_subway_line_events_list_item_title)
        val mTextViewBody: TextView = mView.findViewById(R.id.fragment_subway_line_events_list_item_body)
        var mItem: SubwayLineEvent? = null
    }
}
