package com.phoenixigris.quizz.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.databinding.ActivityMainBinding
import com.phoenixigris.quizz.ui.addquiz.AddQuizActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView.menu.clear()
        binding.bottomNavigationView.inflateMenu(R.menu.home_menu)
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.let {
            binding.bottomNavigationView.setupWithNavController(
                it.findNavController()
            )
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddQuizActivity::class.java))
        }
        setContentView(binding.root)
    }
}