package com.example.app_ointmentt.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.app_ointmentt.R
import com.example.app_ointmentt.ui.doctor.HomePageDoctor
import com.example.app_ointmentt.ui.patient.HomePagePatient
import com.example.app_ointmentt.ui.patient.LoginPatient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userID = FirebaseAuth.getInstance().uid
        val animLogo = AnimationUtils.loadAnimation(this,
            R.anim.dropanimation
        )
        val logo = logoImageView
        logo.startAnimation(animLogo)

        Handler().postDelayed(
            {

                if(userID != null){

                    val refPat = FirebaseFirestore.getInstance().collection("Patients")
                    val refDoc = FirebaseFirestore.getInstance().collection("Doctors")

                    refPat.document("$userID").addSnapshotListener { documentSnapshot, _ ->

                        if(documentSnapshot?.exists()!!){
                            startActivity(Intent(this, HomePagePatient::class.java))
                            finish()
                        }
                    }

                    refDoc.document("$userID").addSnapshotListener { documentSnapshot, _ ->

                        if(documentSnapshot?.exists()!!){
                            startActivity(Intent(this, HomePageDoctor::class.java))
                            finish()
                        }
                    }
                }else {
                    startActivity(Intent(this, LoginPatient::class.java))
                    finish()
                }
            }, 1000)
    }
}