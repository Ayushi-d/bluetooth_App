package com.example.f1bleapp.UI.AlertDialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.f1bleapp.R
import com.example.f1bleapp.databinding.AlertDialogConnectingBinding

@Suppress("UNREACHABLE_CODE")
class ConnectingDialog : DialogFragment() {
    private lateinit var binding: AlertDialogConnectingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alert_dialog_connecting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = AlertDialogConnectingBinding.bind(view)
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}