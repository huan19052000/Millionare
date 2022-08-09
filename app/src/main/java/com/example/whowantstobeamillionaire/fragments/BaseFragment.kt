package com.example.whowantstobeamillionaire.fragments

import androidx.fragment.app.Fragment
import com.example.whowantstobeamillionaire.baseactivity.BaseActivity

open class BaseFragment: Fragment() {
    open fun onBackPressForFragment(){
        if ( activity is BaseActivity){
            (activity as BaseActivity).onBackRoot()
        }
    }
    open fun onFinish1(){
        if ( activity is BaseActivity){
            (activity as BaseActivity).onFinish1()
        }
    }
}