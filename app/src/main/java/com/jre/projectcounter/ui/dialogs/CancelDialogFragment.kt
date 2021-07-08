package com.jre.projectcounter.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.jre.projectcounter.R

class CancelDialogFragment: DialogFragment() {
    companion object {
        const val TAG = "CancelDialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.cancel_dialog))
            .setPositiveButton(R.string.ok) {
                    _, _ -> parentFragment?.findNavController()?.navigateUp()
            }
            .setNegativeButton(R.string.back) {
                _, _ -> dismiss()
            }.create()
}