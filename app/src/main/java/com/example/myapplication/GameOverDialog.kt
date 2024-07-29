package com.example.myapplication

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class GameOverDialog(val resetGame: (()->Unit)?) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Game Over")
                .setNeutralButton("OK") { dialog, id ->
                    resetGame?.invoke()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}