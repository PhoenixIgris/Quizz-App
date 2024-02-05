package com.phoenixigris.quizz.ui.others

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.databinding.ActivitySettingThingUpBinding
import com.phoenixigris.quizz.ui.MainActivity
import com.phoenixigris.quizz.ui.login.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingThingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingThingUpBinding
    private val viewModel: SettingThingsUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivitySettingThingUpBinding.inflate(layoutInflater)
        observeFetchingFinishedOrNot()
        binding.settingThingsUpActLoadingProgressBar.setAnimation(R.raw.loading)
        viewModel.fetchEverythingNeeded()
        setContentView(binding.root)
    }

    private fun observeFetchingFinishedOrNot() {
        viewModel.fetchCompleteLiveData.observe(this) { fetchComplete ->
            if (fetchComplete) {
                lifecycleScope.launch {
                    if (viewModel.isUserLoggedIn) {
                        delay(5000)
                        startActivityFinishingCurrentOne(MainActivity::class.java)
                    } else {
                        startActivityFinishingCurrentOne(LoginActivity::class.java)
                    }
                }

            }
        }
    }

    private fun <T> startActivityFinishingCurrentOne(className: Class<T>) {
        startActivity(Intent(this, className))
        finishAffinity()
    }
}