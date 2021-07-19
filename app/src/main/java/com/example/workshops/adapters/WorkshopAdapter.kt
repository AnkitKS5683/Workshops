package com.example.workshops.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.workshops.R
import com.example.workshops.activities.MainActivity
import com.example.workshops.activities.createAlertDialog
import com.example.workshops.fragments.auth.AuthFragment
import com.example.workshops.fragments.mainscreen.StudentDashboardFragment
import com.example.workshops.models.WorkshopModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.list_item_workshop.view.*

const val APPLIED_WORKSHOPS_IDS = "appliedWorkshopsIds"

class WorkshopAdapter(
    private val activityRef: Activity,
    private val fragment: Fragment,
    private val workshops: ArrayList<WorkshopModel>
) : RecyclerView.Adapter<WorkshopAdapter.WorkshopViewHolder>() {

    private val prefs = activityRef.getPreferences(Context.MODE_PRIVATE)

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopViewHolder =
        WorkshopViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_workshop, parent, false)
        )

    override fun onBindViewHolder(holder: WorkshopViewHolder, position: Int) {
        holder.itemView.apply {
            if (fragment is StudentDashboardFragment) {
                applyBtn.visibility = View.GONE
            } else {
                if (auth.currentUser != null && prefs.getStringSet(
                        auth.uid.toString() + APPLIED_WORKSHOPS_IDS,
                        HashSet<String>()
                    )
                        ?.contains(workshops[position].id.toString()) == true
                ) {
                    applyBtn.apply {
                        text = context.getString(R.string.applied)
                        setBackgroundColor(
                            ContextCompat.getColor(
                                activityRef,
                                R.color.light_blue
                            )
                        )
                        isEnabled = false
                    }
                } else {
                    applyBtn.apply {
                        text = context.getString(R.string.apply)
                        setBackgroundColor(
                            ContextCompat.getColor(
                                activityRef,
                                R.color.dark_blue
                            )
                        )
                        isEnabled = true
                    }

                    applyBtn.setOnClickListener {
                        if (auth.currentUser != null && auth.currentUser?.isEmailVerified == true) {
                            activityRef.createAlertDialog(
                                "Confirm Apply",
                                "Are you sure you want to apply for ${workshops[position].title} workshop?",
                                "Yes",
                                "Cancel"
                            ) {
                                val modifiedAppliedWorkshopsIds = HashSet<String>(
                                    prefs.getStringSet(
                                        auth.uid.toString() + APPLIED_WORKSHOPS_IDS,
                                        HashSet<String>()
                                    )
                                )

                                modifiedAppliedWorkshopsIds.add(workshops[position].id.toString())

                                prefs.edit {
                                    putStringSet(
                                        auth.uid.toString() + APPLIED_WORKSHOPS_IDS,
                                        modifiedAppliedWorkshopsIds
                                    )
                                }

                                applyBtn.apply {
                                    text = context.getString(R.string.applied)
                                    setBackgroundColor(
                                        ContextCompat.getColor(
                                            activityRef,
                                            R.color.light_blue
                                        )
                                    )
                                    isEnabled = false
                                }

                                Toast.makeText(
                                    activityRef,
                                    "Applied Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            (activityRef as MainActivity).setFragmentWithBackstack(AuthFragment())
                        }
                    }
                }
            }

            Glide.with(this).load(workshops[position].imageId).into(imgView)

            tvTitle.text = workshops[position].title
            tvDescription.text = workshops[position].description
            tvTime.text = workshops[position].dateNTime
        }
    }

    override fun getItemCount() = workshops.size

    class WorkshopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}