package com.example.workshops.fragments.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workshops.R
import com.example.workshops.adapters.WorkshopAdapter
import com.example.workshops.database.DbHelper
import com.example.workshops.database.WorkshopTable
import com.example.workshops.models.WorkshopModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_available_workshops.*
import kotlinx.android.synthetic.main.fragment_workshops_container.*

class AvailableWorkshopsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_workshops, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = DbHelper(requireContext()).writableDatabase

        var availableWorkshops = WorkshopTable.getAllWorkshops(db)

        if (availableWorkshops.size == 0) {
            val hardCodedWorkshops = WorkshopModel.getHardCodedWorkshops()

            for (workshop in hardCodedWorkshops) {
                WorkshopTable.insertWorkshop(db, workshop)
            }

            availableWorkshops = WorkshopTable.getAllWorkshops(db)
        }

        availableWorkshops[availableWorkshops.size - 1].let { mostRecent ->
            Glide.with(this).load(mostRecent.imageId).into(recentImgView)
            recentTvTitle.text = mostRecent.title
            recentTvTime.text = mostRecent.dateNTime
        }

        rvAvbWorkshops.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            adapter = WorkshopAdapter(
                requireActivity(),
                this@AvailableWorkshopsFragment,
                availableWorkshops
            )
            scrollToPosition(availableWorkshops.size - 1)
        }
    }
}