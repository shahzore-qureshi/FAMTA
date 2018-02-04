package com.shahzorequreshi.famta.fragments

import android.content.Context
import android.support.v4.app.DialogFragment
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.shahzorequreshi.famta.R

/**
 * Represents a dialog that describes why the user's location is being requested.
 */
class LocationRequestDialogFragment : DialogFragment() {
    private var mListener: OnLocationRequestDialogFragmentInteractionListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is OnLocationRequestDialogFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnLocationRequestDialogFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(R.string.use_location)
                .setPositiveButton(R.string.yes_please, { _, _ ->
                    mListener?.onDialogPositiveClick(this)
                })
                .setNegativeButton(R.string.no_thanks, { _, _ ->
                    mListener?.onDialogNegativeClick(this)
                })
        return builder.create()
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        mListener?.onDialogNegativeClick(this)
    }
    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        mListener?.onDialogNegativeClick(this)
    }

    interface OnLocationRequestDialogFragmentInteractionListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }
}