package com.phoenixigris.quizz.ui.profile

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.database.DataStoreHelper
import com.phoenixigris.quizz.databinding.FragmentProfileBinding
import com.phoenixigris.quizz.ui.others.SettingThingUpActivity
import com.phoenixigris.quizz.utils.HelperClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.profileUserCard.background = HelperClass.linearGradientDrawable(
            "#5C71E4",
            "#4C93EE",
            "#55B9F9",
            GradientDrawable.Orientation.LEFT_RIGHT, 60F
        )
        setValues()
        setUpMenu()
        return binding.root
    }

    private fun setUpMenu() {
        binding.menu.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.menu)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.profile_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.logout -> {
                        logOut()
                        true
                    }

                    else -> false
                }
            }

            popup.show()
        }
    }

    private fun logOut() {
        lifecycleScope.launch {
            FirebaseAuth.getInstance().signOut()
            dataStoreHelper.clear()
            startActivity(Intent(requireContext(), SettingThingUpActivity::class.java))
        }
    }

    private fun setValues() {
        lifecycleScope.launch {
            val list = dataStoreHelper.getQuizStatusList().first()
            Log.e("Debug", "setValues: $list")
            list.let {
                binding.profileGridScoreLyt.apply {
                    layoutManager = GridLayoutManager(requireContext(), 1)
                    adapter = ScoreRVA(it)
                }
            }
        }
    }

}