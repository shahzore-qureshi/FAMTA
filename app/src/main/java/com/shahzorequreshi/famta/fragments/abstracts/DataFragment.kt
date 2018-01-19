package com.shahzorequreshi.famta.fragments.abstracts

import android.support.v4.app.Fragment

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
abstract class DataFragment : Fragment() {
    abstract fun updateData(data: List<*>)
}