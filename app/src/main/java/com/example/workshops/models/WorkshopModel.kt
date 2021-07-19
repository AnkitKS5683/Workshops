package com.example.workshops.models

import com.example.workshops.R

class WorkshopModel(
    val imageId: Int,
    val title: String,
    val description: String,
    val dateNTime: String,
    val id: Int? = null
) {
    companion object {
        fun getHardCodedWorkshops(): ArrayList<WorkshopModel> = arrayListOf(

            WorkshopModel(
                R.drawable.computer_vision,
                "Computer Vision",
                "An innovation under AI that enables robots to identify, analyze and understand important information from one or many images.",
                "12th June, 1:00 pm"
            ),
            WorkshopModel(
                R.drawable.fraud_detection,
                "Fraud Detection",
                "Learn about various machine learning algorithms and develop a fraud detection project using Python language.",
                "14th June, 3:00 pm"
            ),
            WorkshopModel(
                R.drawable.home_automation,
                "Home Automation IOT",
                "Develop a Home Automation System using Bluetooth technology. Remotely control home appliances with your smartphone and an Android app.",
                "16th June, 4:30 pm"
            ),
            WorkshopModel(
                R.drawable.cnc_ml,
                "CNC Machine Learning",
                "Be part of a revolution that includes machines that respond to Alexa-like voice commands and machine scheduling for optimized performance.",
                "18th June, 5:30 pm"
            ),
            WorkshopModel(
                R.drawable.automic_solar_system,
                "Automatic Solar Tracker",
                "Devices which automatically orient in the direction of high intensity sunlight. Develop one using a solar panel, LDR and DC Motors based on Arduino",
                "20th June, 8:00 pm"
            ),
            WorkshopModel(
                R.drawable.sixth_sense_robot,
                "Sixth Sense Robot",
                "Revolutionary technology that can change the physical world around us. Use natural hand gestures to control all the electronic devices.",
                "22th June, 4:00 pm"
            ),
            WorkshopModel(
                R.drawable.intro_to_git,
                "Introduction To Git",
                "This workshop will provide a gentle introduction to Git aimed at a general audience. You will attain some idea of what Git is and how to begin using it.",
                "24th June, 6:00 pm"
            ),
            WorkshopModel(
                R.drawable.tech_impact,
                "Tech Impact 2021",
                "Technology affects the way individuals communicate, learn, and think. Analyse the impact of emerging technologies on our lifestyle, education and jobs.",
                "28th June, 2:00 pm"
            ),
        )
    }
}