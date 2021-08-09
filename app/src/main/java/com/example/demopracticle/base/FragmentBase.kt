package com.example.demopracticle.base


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.demopracticle.R


/**
 * Created by jaydip.
 */

abstract class FragmentBase<V : ViewModelBase> : Fragment() {
    private var mViewModel: V? = null
    private var baseActivity: ActivityBase<*>? = null

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
        setHasOptionsMenu(false)
    }

    /**
     * Get View model of current page
     */
    private fun setUpViewModel() {
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActivityBase<*>) {
            this.baseActivity = context
        }
    }


    override fun onDetach() {
        baseActivity = null
        super.onDetach()
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.cancel()
        }
    }

    private fun setUpSnackbar() {
        viewModel.snackbarMessage.observe(this, Observer { o ->
            if (o == null) {
                return@Observer
            }
            if (baseActivity != null) {
                if (o is Int) {
                    baseActivity!!.showSnackbar(getString((o as Int?)!!))
                } else if (o is String) {
                    baseActivity!!.showSnackbar((o as String?)!!)
                }
            }
        })
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

    private fun setUpHideKeyBoard() {
        viewModel.hideKeyBoardEvent.observe(this, { hideKeyboard() })
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    @SuppressLint("InflateParams")
    fun createLoadingDialog() {
        if (loadingDialog != null && loadingDialog?.isShowing!!) {
            return
        }

        val adb =
            AlertDialog.Builder(requireContext(), android.R.style.Theme_Translucent_NoTitleBar)
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.layout_loading_dialog, null)
        adb.setCancelable(false)
        adb.setView(dialogView)
        adb.setIcon(R.mipmap.ic_launcher)
        loadingDialog = adb.create()
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                loadingDialog!!.show()
            }
        }, 100)
    }

    fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog?.dismiss()
        }

    }


    protected abstract fun setUp(view: View)
}
