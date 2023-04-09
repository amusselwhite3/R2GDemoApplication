package com.austin.rooms2godemoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.austin.rooms2godemoapp.data.Email


/**
 * A fragment representing a list of Items.
 */
class EmailListFragment : Fragment() {

    private var columnCount = 1
    val viewModel: SharedEmailViewModel by activityViewModels()
    var listData: List<Email> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_email_list_list, container, false)
        // Set the adapter
        val toolbar: Toolbar = view.findViewById(R.id.toolbar) as Toolbar

        toolbar.setNavigationOnClickListener {
            // back button pressed
            activity?.onBackPressed()
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)


        if (recyclerView != null) {
            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                this.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        VERTICAL
                    )
                )

                adapter = viewModel.emailData.value?.let { OrderRecyclerViewAdapter(it) }
            }
        }
        updateData()
        return view
    }
    fun updateData() {
        viewModel.emailData.observe(viewLifecycleOwner) { data ->
            listData = data
        }
    }

    override fun onDestroyView() {
        viewModel.clearData()
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }
    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance() =
            EmailListFragment().apply {
            }
    }
}