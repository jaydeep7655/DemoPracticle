package com.example.demopracticle

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demopracticle.base.ActivityBase
import com.example.demopracticle.databinding.*
import com.example.demopracticle.model.ModelMain
import com.example.demopracticle.viewmodel.ViewModelActivityLog
import com.example.demopracticle.viewmodel.ViewModelActivityMain

class LogsActivity : ActivityBase<ViewModelActivityLog>() {
    private var adapterActivityLog: AdapterActivityLog? = null

    override val viewModel: ViewModelActivityLog
        get() = ViewModelProvider(this).get(ViewModelActivityLog::class.java)
    private lateinit var mContext: Context
    private lateinit var bindingObj: ActivityLogsBinding
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        bindingObj = DataBindingUtil.setContentView(this, R.layout.activity_logs)
        bindingObj.vmObj = this.viewModel
        setUpToolbar()
        initComponent()
        setUpEvents()

    }

    private fun setUpToolbar() {
        if (intent != null && intent.hasExtra("toLogActivity")) {
            viewModel.list =
                this.intent.getSerializableExtra("toLogActivity") as ArrayList<ModelMain>
        }



        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.log)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        setSupportActionBar(toolbar)
    }
    private fun initComponent() {
        val linearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        bindingObj.rvLog.layoutManager = linearLayoutManager
        bindingObj.rvLog.isNestedScrollingEnabled = false
        setUpRecyclerview()
    }
    private fun setUpEvents() {
        viewModel.getLiveClickEvent().observe(this, { viewId ->
            if (viewId != null) {
                when (viewId) {


                }
            }
        })

    }

    private fun setUpRecyclerview() {
            adapterActivityLog = AdapterActivityLog(viewModel.list)
            bindingObj.rvLog.adapter = adapterActivityLog

    }

    internal inner class AdapterActivityLog(val list: ArrayList<ModelMain>) :
        RecyclerView.Adapter<AdapterActivityLog.MyViewHolder>() {
        private lateinit var itemList: ItemLogHorizontalBinding

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterActivityLog.MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            itemList =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_log_horizontal, parent, false)
            return MyViewHolder(itemList)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        internal inner class MyViewHolder(private var itemList: ItemLogHorizontalBinding) :
            RecyclerView.ViewHolder(itemList.root) {
            fun bind(model: ModelMain) {
                itemList.tvLog.text = "Raw"+adapterPosition
                for(i in 0..model.list.size-1){
                    itemList.tvLog.append(" C${model.position}(${model.list[i].value})")
                }

            }

        }


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