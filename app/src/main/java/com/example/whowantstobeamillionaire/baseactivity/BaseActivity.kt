package com.example.whowantstobeamillionaire.baseactivity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.whowantstobeamillionaire.fragments.BaseFragment

open class BaseActivity : AppCompatActivity(){

    private fun getCurrentFragment(): Fragment? {
//        supportFragmentManager.fragments
        //lay các fragment do supportFragmentManager của activity quản
        for (fragment in supportFragmentManager.fragments) {
            if (fragment != null && fragment.isVisible) {
                return fragment
            }
        }
        return null
    }

    fun onBackRoot() {
        super.onBackPressed()
    }

    fun onFinish1() {
        finish()
    }

    override fun onBackPressed() {
        val fr = getCurrentFragment()
        if (fr != null && fr is BaseFragment){
            fr.onBackPressForFragment()
        }else {
            onBackRoot()
        }
    }
}