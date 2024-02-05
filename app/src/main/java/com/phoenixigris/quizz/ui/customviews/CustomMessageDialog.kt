package com.phoenixigris.quizz.ui.customviews

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.phoenixigris.quizz.R

object CustomMessageDialog {

    fun showCustomMessageDialog(context: Context) {
        val selectModeDialog =
            AlertDialog.Builder(context, R.style.CustomDialogTheme).apply {
                setCancelable(true)
                setView(
                    LayoutInflater.from(context).inflate(R.layout.fragment_mode_select_dialog, null)
                        .apply {

                        })
            }.create()
        selectModeDialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setGravity(Gravity.CENTER)
        }
        selectModeDialog.show()
    }
}