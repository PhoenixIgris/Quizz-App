package com.phoenixigris.quizz.ui.profile

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.phoenixigris.quizz.database.DataStoreHelper
import com.phoenixigris.quizz.databinding.ActivityProfileBinding
import com.phoenixigris.quizz.utils.HelperClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding.profileUserCard.background = HelperClass.linearGradientDrawable(
            "#5C71E4",
            "#4C93EE",
            "#55B9F9",
            GradientDrawable.Orientation.LEFT_RIGHT, 60F
        )
        binding.profileBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        setValues()
        setContentView(binding.root)
    }

    private fun setValues() {
        lifecycleScope.launch {
            dataStoreHelper.getQuizStatusList().first().let {
                binding.profileGridScoreLyt.apply {
                    layoutManager = GridLayoutManager(this@ProfileActivity, 2)
                    adapter = ScoreRVA(it)
                }
            }
        }
    }
}