package com.phoenixigris.quizz.ui.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.databinding.FragmentComingSoonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComingSoonFragment : Fragment() {
    private lateinit var binding: FragmentComingSoonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComingSoonBinding.inflate(layoutInflater)
        return binding.root
    }

}