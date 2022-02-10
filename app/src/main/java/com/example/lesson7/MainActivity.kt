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

        binding.mainMenu.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.local_weather -> {
                    bundle.putString(MainFragment.BUNDLE_EXTRA_MENU, "isRussian")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance(bundle))
                        .commitNow()
                    true
                }

                R.id.world_weather -> {
                    bundle.putString(MainFragment.BUNDLE_EXTRA_MENU, "isWorld")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance(bundle))
                        .commitNow()
                    true
                }

                R.id.settings -> {
                    true
                }

                else -> false
            }
        }
    }
}