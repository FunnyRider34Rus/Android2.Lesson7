package com.example.lesson7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson7.databinding.ActivityMainBinding
import com.example.lesson7.view.MainFragment
import com.example.lesson7.view.MainFragment.Companion.bundle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(bundle))
                .commitNow()
        }
    }
}