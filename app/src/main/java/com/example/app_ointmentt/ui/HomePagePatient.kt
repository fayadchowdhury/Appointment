package com.example.app_ointmentt.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.app_ointmentt.*
import com.example.app_ointmentt.ui.fragment.PatientHistoryFragment
import com.example.app_ointmentt.ui.fragment.PatientHomeFragment
import com.example.app_ointmentt.ui.fragment.PatientNotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_page_patient.*
import kotlinx.android.synthetic.main.activity_home_page_patient.bottomNavigationView
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

class HomePagePatient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_patient)
        changeToolbarTitle(toolbar = toolbar,txt = "Appointment")
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        loadHomeFragment()
    }
    private fun loadHomeFragment(){
        supportFragmentManager.beginTransaction().replace(
            R.id.main,
            PatientHomeFragment(),
            PatientHomeFragment().javaClass.simpleName)
            .commit()
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.home -> {
                changeToolbarTitle(toolbar = toolbar,txt = "Appointment")
                val fragment =
                    PatientHomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.history -> {
                changeToolbarTitle(toolbar = toolbar,txt = "History")
                val fragment =
                    PatientHistoryFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.notification -> {
                changeToolbarTitle(toolbar = toolbar,txt = "Notification")
                val fragment =
                    PatientNotificationFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun changeToolbarTitle(toolbar: View?, txt: String){
        toolbar!!.toolbarTitle.setText(txt)
    }

}
