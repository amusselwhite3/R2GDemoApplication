package com.austin.rooms2godemoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels


class EmailFragment : Fragment() {

    companion object {
        fun newInstance() = EmailFragment()
    }

    val viewModel: SharedEmailViewModel by activityViewModels()
    private var emailButton: Button? = null
    private var errorText: TextView? = null
    private var spinner: FrameLayout? = null
    private var editText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_email, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        emailButton = view.findViewById(R.id.go_button)
        errorText = view.findViewById(R.id.errorText)
        spinner = view.findViewById(R.id.spinner)
        editText = view.findViewById(R.id.input_box)
        createLoadingLiveData()
        processNetworkResult()
        emailButton?.setOnClickListener {
            val editText = editText?.text.toString()
            viewModel.getEmailData(editText)
        }
    }

    fun createLoadingLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            if (isLoading) {
                spinner?.visibility = View.VISIBLE
            } else {
                spinner?.visibility = View.GONE
            }
        }
    }

    fun processNetworkResult() {
        viewModel.success.observe(viewLifecycleOwner) { success ->
            if (success && !viewModel.emailData.value.isNullOrEmpty()) {
                errorText?.visibility = View.GONE
                activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.activity_container, EmailListFragment.newInstance())
                    ?.addToBackStack("Email")
                ?.commit()
            }  else {
                errorText?.text = viewModel.errorText.value
                errorText?.visibility = View.VISIBLE
            }
        }
    }
}