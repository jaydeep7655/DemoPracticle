package com.example.demopracticle

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.demopracticle.base.ActivityBase
import com.example.demopracticle.databinding.ActivityMainBinding
import com.example.demopracticle.databinding.ActivitySettingBinding
import com.example.demopracticle.viewmodel.ViewModelActivityMain

class SettingActivity : ActivityBase<ViewModelActivityMain>() {


    override val viewModel: ViewModelActivityMain
        get() = ViewModelProvider(this).get(ViewModelActivityMain::class.java)
    private lateinit var mContext: Context
    private lateinit var bindingObj: ActivitySettingBinding
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        bindingObj = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        bindingObj.vmObj = this.viewModel
        setUpToolbar()
        setUpEvents()

    }

    private fun setUpToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.setting)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        setSupportActionBar(toolbar)
    }

    private fun setUpEvents() {
        viewModel.getLiveClickEvent().observe(this, { viewId ->
            if (viewId != null) {
                when (viewId) {
                    R.id.btnSubmit -> {
                        val resultIntent=Intent()
                        resultIntent.putExtra("countCheckBox",viewModel.number.get())
                        setResult(Activity.RESULT_OK,resultIntent)
                        finish()
                    }

                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}