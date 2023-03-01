package com.phoenixigris.quizz.ui.others

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.phoenixigris.quizz.databinding.ActivitySettingThingUpBinding
import com.phoenixigris.quizz.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingThingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingThingUpBinding
    private val viewModel: SettingThingsUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingThingUpBinding.inflate(layoutInflater)
        observeFetchingFinishedOrNot()
        viewModel.fetchEverythingNeeded()
        setContentView(binding.root)
    }

    private fun observeFetchingFinishedOrNot() {
        viewModel.fetchCompleteLiveData.observe(this) { fetchComplete ->
            if (fetchComplete) {
                startActivityFinishingCurrentOne(HomeActivity::class.java)
            }
        }
    }

    private fun <T> startActivityFinishingCurrentOne(className: Class<T>) {
        startActivity(Intent(this, className))
        finishAffinity()
    }
}