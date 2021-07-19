package com.example.workshops.fragments.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.workshops.R
import com.example.workshops.activities.MainActivity
import com.example.workshops.activities.createAlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_workshops_container.*

class WorkshopsContainerFragment : Fragment() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workshops_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (auth.currentUser != null && auth.currentUser?.isEmailVerified == true) {
            btnHome.visibility = View.VISIBLE
            btnSignOut.visibility = View.VISIBLE
            setDashboard()
        } else {
            setAvailableWorkshops()
        }

        btnHome.setOnClickListener {
            btnAvbWorkshops.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.light_blue
                )
            )
            setDashboard()
        }

        btnAvbWorkshops.setOnClickListener {
            btnHome.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_blue))
            setAvailableWorkshops()
        }

        btnSignOut.setOnClickListener {
            requireContext().createAlertDialog(
                "Confirm Sign Out",
                "Are you sure you want to sign out of Workshopedia",
                "Sign Out",
                "Cancel"
            ) {
                auth.signOut()
                (activity as MainActivity).setFragmentWithoutBackstack(WorkshopsContainerFragment())
            }
        }
    }

    private fun setDashboard() {
        btnHome.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        tvHeading.text = requireContext().getString(R.string.student_dashboard)
        setFragment(StudentDashboardFragment())
    }

    private fun setAvailableWorkshops() {
        btnAvbWorkshops.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.dark_blue
            )
        )
        tvHeading.text = getString(R.string.available_workshops)
        setFragment(AvailableWorkshopsFragment())
    }

    private fun setFragment(fragmentToSet: Fragment) {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .replace(R.id.child_fragment_container, fragmentToSet)
            .commit()
    }
}