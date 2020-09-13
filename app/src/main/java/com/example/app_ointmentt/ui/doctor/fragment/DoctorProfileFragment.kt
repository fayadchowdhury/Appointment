package com.example.app_ointmentt.ui.doctor.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.app_ointmentt.IHomepage
import com.example.app_ointmentt.R
import com.example.app_ointmentt.databasing.DoctorDB
import com.example.app_ointmentt.models.Doctor
import com.example.app_ointmentt.ui.patient.LoginPatient
import com.example.app_ointmentt.utils.invokeBottomModalSheet
import kotlinx.android.synthetic.main.fragment_doctor_profile.*
import kotlinx.android.synthetic.main.fragment_doctor_profile.view.*
import kotlinx.android.synthetic.main.fragment_patient_profile.ProfileUsername

class DoctorProfileFragment: Fragment(),
DoctorDB.GetDoctorByIdSuccessListener,
DoctorDB.GetDoctorByIdFailureListener{
    private lateinit var iHomeFragment: IHomepage

    //sharedPreference File Name
    private val sharedPrefFile = "appointmentSharedPref"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iHomeFragment.setToolbarTitle("Profile")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view =  inflater.inflate(R.layout.fragment_doctor_profile,container,false)
        val context = activity?.applicationContext
        if (context != null) {
            setDoctorProfileFromAPI(view,context)
        }
        view.doctorUserNameEditBtn.setOnClickListener {
            invokeBottomModalSheet(it)
        }

        view.doctorLocationEditBtn.setOnClickListener {
            invokeBottomModalSheet(it)
        }
        view.doctorBloodGroupEditBtn.setOnClickListener {
            invokeBottomModalSheet(it)
        }

        view.doctorDOBEditBtn.setOnClickListener {
            invokeBottomModalSheet(it)
        }

        view.doctorEmailEditBtn.setOnClickListener {
            invokeBottomModalSheet(it)
        }
        view.doctorGenderEditBtn.setOnClickListener {
            invokeBottomModalSheet(it)
        }
        view.doctorTypeEditBtn.setOnClickListener {
            invokeBottomModalSheet(it)
        }
        view.doctorBMDCEditBtn.setOnClickListener {
            invokeBottomModalSheet(it)
        }
        view.doctorPhoneNumberEditBtn.setOnClickListener {
            invokeBottomModalSheet(it)
        }

        view.doctorLogoutBtn.setOnClickListener {
            val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("jwt", null)
            editor.putString("uid", null)
            editor.putString("type", null)
            editor.apply()
            val intent = Intent(activity, LoginPatient::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity!!.startActivity(intent)
            activity!!.finish()
        }
        return view
    }

    private fun setDoctorProfileFromAPI(view: View?,context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        val doctorDB = DoctorDB(context)
        doctorDB.setGetDoctorByIDSuccessListener(this)
        doctorDB.setGetDoctorByIDFailureListener(this)
        val uid = sharedPreferences.getString("uid","")
        if (uid != null) {
            doctorDB.getDoctorByID(uid)
        }
        else
            return

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        iHomeFragment = activity as IHomepage
    }

    override fun getDoctorByIDSuccess(doctor: Doctor) {
        ProfileUsername.text = doctor.name
        doctor_email_address.text = doctor.email
        doctor_bmdc.text = doctor.bmdc
        if(doctor.isDOBInitialized()){
            doctor_dob.text = doctor.dob
        }
        if(doctor.isGenderInitialized()){
            doctor_gender.text = doctor.gender
        }
        if(doctor.isAddressInitialized()){
            doctor_address.text = doctor.address

        }
        if(doctor.isPhoneInitialized()){
            doctor_phone.text = doctor.phone
        }
        if(doctor.isBloodInitialized()){
            doctor_blood_group.text = doctor.blood
        }
        if(doctor.isSpecialtyInitialized()){
            doctor_specialty.text  = doctor.specialty
        }
    }

    override fun getDoctorByIDFailure() {
        Toast.makeText(context,"Failed to get User Profile. Please Try Later", Toast.LENGTH_SHORT).show()
    }
}