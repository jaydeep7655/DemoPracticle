package com.example.demopracticle.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.demopracticle.R
import com.google.android.material.snackbar.Snackbar

abstract class ActivityBase<V : ViewModelBase> : AppCompatActivity() {
    private var mViewModel: V? = null
    private var loadingDialog: AlertDialog? = null

    /**
     * Override for get view model
     *
     * @return
     */
    abstract val viewModel: V
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
        setUpSnackbar()
        setUpHideKeyBoard()
        setupLoadingDialog()
    }

    /**
     * Get View model of current page
     */
    private fun setUpViewModel() {
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
    }

    private fun setUpHideKeyBoard() {
        viewModel.hideKeyBoardEvent.observe(this, { hideKeyboard() })
    }

    /**
     * Method observe for show snackBar
     */
    private fun setUpSnackbar() {
        mViewModel!!.snackbarMessage.observe(this, { data ->
            if (data != null) {
                if (data is Int) {
                    showSnackbar(getString((data as Int?)!!))
                } else if (data is String) {
                    showSnackbar(data)
                }
            }
        }

        )
    }

    private fun setupLoadingDialog() {
        mViewModel!!.loadingDialog.observe(this, { data ->
            if (data != null) {
                if (data) {
                    createLoadingDialog()
                } else {
                    loadingDialog?.dismiss()
                }
            }
        })
    }

    /**
     * Method for show snackBar
     *
     * @param message
     */
    fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG
        )

        val view = snackbar.view
        val snackTV = view.findViewById<TextView>(R.id.snackbar_text)
        snackTV.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

    fun showSnackbar(message: Int) {
        val snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG
        )

        val view = snackbar.view
        val snackTV = view.findViewById<TextView>(R.id.snackbar_text)
        snackTV.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    @SuppressLint("InflateParams")
    fun createLoadingDialog() {
        if (loadingDialog != null && loadingDialog?.isShowing!!) {
            return
        }

        val adb = AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.layout_loading_dialog, null)
        adb.setCancelable(false)
        adb.setView(dialogView)
        adb.setIcon(R.mipmap.ic_launcher)
        loadingDialog = adb.create()
        loadingDialog!!.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.cancel()
        }
    }

}
