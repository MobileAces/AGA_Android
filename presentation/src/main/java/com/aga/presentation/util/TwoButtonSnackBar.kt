package com.aga.presentation.util

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.aga.presentation.R
import com.google.android.material.snackbar.Snackbar

class TwoButtonSnackBar private constructor() {

    private lateinit var snackBar: Snackbar
    private var cancelListener: ()-> Unit = {}
    private var positiveListener: ()->Unit = {}

    private lateinit var customSnackbarView: View
    private lateinit var snackbarLayout: Snackbar.SnackbarLayout

    constructor(view: View, text: String, duration: Int): this(){
        customSnackbarView = LayoutInflater.from(view.context).inflate(R.layout.snackbar_two_button, null)
        customSnackbarView.findViewById<TextView>(R.id.snackbar_text).text = text
        customSnackbarView.findViewById<TextView>(R.id.btn_cancel).setOnClickListener {
            cancelListener()
            snackBar.dismiss()
        }
        customSnackbarView.findViewById<TextView>(R.id.btn_confirm).setOnClickListener {
            positiveListener()
            snackBar.dismiss()
        }
        snackBar = Snackbar.make(view, text, duration)
        snackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackbarView,0)
    }

    fun show(){
        snackBar.show()
    }

    fun setCancelClickListener(listener: ()->Unit): Snackbar{
        cancelListener = listener
        return snackBar
    }

    fun setConfirmClickListener(listener: ()->Unit): Snackbar{
        positiveListener = listener
        return snackBar
    }
}