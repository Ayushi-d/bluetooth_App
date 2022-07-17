package com.example.f1bleapp.UI.AlertDialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.f1bleapp.R
import com.example.f1bleapp.databinding.AlertDailogSuccessBinding

@Suppress("UNREACHABLE_CODE")
class SuccessDialog() : DialogFragment() {
    private lateinit var binding: AlertDailogSuccessBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alert_dailog_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AlertDailogSuccessBinding.bind(view)
        binding.alertDialogBtnOk.setOnClickListener {
            dismissSuccessDialog()
        }

    }

    private fun dismissSuccessDialog() {
        dismiss()
    }

}