package com.example.lesson7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.lesson7.databinding.ActivityMainBinding
import com.example.lesson7.view.HistoryFragment
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
                .setReorderingAllowed(true)
                .addToBackStack("MAIN")
                .commit()
        }

        binding.mainMenu.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.local_weather -> {
                    bundle.putString(MainFragment.BUNDLE_EXTRA_MENU, "isRussian")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance(bundle))
                        .commit()
                    true
                }

                R.id.world_weather -> {
                    bundle.putString(MainFragment.BUNDLE_EXTRA_MENU, "isWorld")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance(bundle))
                        .commit()
                    true
                }

                R.id.settings -> {
                    true
                }

                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { return when (item.itemId) {
        R.id.menu_history -> { supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, HistoryFragment.newInstance())
                .addToBackStack("HISTORY")
                .commit()
        }
            true
        }
        else -> super.onOptionsItemSelected(item) }
    }
}