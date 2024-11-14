package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlbdev.mantapluarbiasa.databinding.ActivityOurScheduleBinding

private val ARG_SCHEDULE = "arrayschedule"

class OurScheduleFragment : Fragment() {
    private var our_schedule: ArrayList<OurScheduleBank> = ArrayList()
    private lateinit var binding: ActivityOurScheduleBinding
    private lateinit var listAdapter: OurScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            our_schedule = it.getParcelableArrayList<OurScheduleBank>(ARG_SCHEDULE) as ArrayList<OurScheduleBank>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityOurScheduleBinding.inflate(inflater, container, false)

        binding.schedulePage.layoutManager = LinearLayoutManager(requireContext())
        binding.schedulePage.setHasFixedSize(true)

        listAdapter = OurScheduleAdapter(our_schedule)
        binding.schedulePage.adapter = listAdapter

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(schedule:ArrayList<OurScheduleBank>) = OurScheduleFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ARG_SCHEDULE, schedule)
            }
        }
    }
}