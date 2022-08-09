package com.example.whowantstobeamillionaire
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.whowantstobeamillionaire.baseactivity.BaseActivity
import com.example.whowantstobeamillionaire.databinding.ActivityMainBinding
import com.example.whowantstobeamillionaire.fragments.FirstFragment


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //media
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragment_container,
                FirstFragment(),
                FirstFragment::class.java.name
            )
            .addToBackStack(null).commit()
    }
}
