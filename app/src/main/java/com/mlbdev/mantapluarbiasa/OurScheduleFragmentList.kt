package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

private val ARG_SCHEDULE = "arrayschedule"

class OurScheduleFragmentList : ListFragment() {
    private var our_schedule: ArrayList<OurScheduleBank> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            our_schedule = it.getParcelableArrayList<OurScheduleBank>(ARG_SCHEDULE) as ArrayList<OurScheduleBank>
            listAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, our_schedule)
        }
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
    }

    companion object {
        @JvmStatic
        fun newInstance(schedule:ArrayList<OurScheduleBank>) = OurScheduleFragmentList().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ARG_SCHEDULE, schedule)
            }
        }
    }
}