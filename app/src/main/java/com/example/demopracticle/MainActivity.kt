package com.example.demopracticle

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demopracticle.base.ActivityBase
import com.example.demopracticle.databinding.ActivityMainBinding
import com.example.demopracticle.databinding.ItemCheckboxHorizontalBinding
import com.example.demopracticle.databinding.ItemCheckboxListBinding
import com.example.demopracticle.model.ModelCheckBox
import com.example.demopracticle.model.ModelMain
import com.example.demopracticle.viewmodel.ViewModelActivityMain

class MainActivity : ActivityBase<ViewModelActivityMain>() {


    override val viewModel: ViewModelActivityMain
        get() = ViewModelProvider(this).get(ViewModelActivityMain::class.java)
    private lateinit var mContext: Context
    private lateinit var bindingObj: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private var adapterCheckBox: AdapterCheckBox? = null
    var item: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        bindingObj = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindingObj.vmObj = this.viewModel
        initComponent()
        setUpToolbar()
        initiateEmployeelistApiCall()
        setUpEvents()
    }

    private fun initComponent() {
        viewModel.initData()
        val linearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        bindingObj.rvCheckbox.layoutManager = linearLayoutManager
        bindingObj.rvCheckbox.isNestedScrollingEnabled = false
        setUpRecyclerview()
    }

    /**
     * set toolbar
     */
    private fun setUpToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        setSupportActionBar(toolbar)
    }

    /**
     * callEmployee List api
     */
    private fun initiateEmployeelistApiCall() {

    }

    private fun setUpEvents() {
        viewModel.getLiveClickEvent().observe(this, { viewId ->
            if (viewId != null) {
                when (viewId) {
                    1 -> {
                        setUpRecyclerview()
                    }
                    R.id.btnLog->{
                        val intent = Intent(mContext, LogsActivity::class.java)
                        intent.putExtra(
                           "toLogActivity",
                            viewModel.list
                        )
                        startActivity(intent)

                    }

                }
            }
        })

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecyclerview() {
            adapterCheckBox = AdapterCheckBox(viewModel.list)
            bindingObj.rvCheckbox.adapter = adapterCheckBox
    }

    internal inner class AdapterCheckBox(val list: ArrayList<ModelMain>) :
        RecyclerView.Adapter<AdapterCheckBox.MyViewHolder>() {
        private lateinit var itemList: ItemCheckboxListBinding

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterCheckBox.MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            itemList =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_checkbox_list, parent, false)
            return MyViewHolder(itemList)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        internal inner class MyViewHolder(private var itemList: ItemCheckboxListBinding) :
            RecyclerView.ViewHolder(itemList.root) {
            fun bind(model: ModelMain) {
                itemList.modelMain = model
                val linearLayoutManager =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                itemList.rvCheckboxHorizontal.layoutManager = linearLayoutManager
                val adapter = AdapterCheckBoxHorizontal(model.list)
                itemList.rvCheckboxHorizontal.adapter = adapter

            }

        }


    }

    internal inner class AdapterCheckBoxHorizontal(val list: List<ModelCheckBox>) :
        RecyclerView.Adapter<AdapterCheckBoxHorizontal.MyViewHolder>() {
        private lateinit var itemCheckboxHorizontalList: ItemCheckboxHorizontalBinding

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterCheckBoxHorizontal.MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            itemCheckboxHorizontalList = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_checkbox_horizontal,
                parent,
                false
            )
            return MyViewHolder(itemCheckboxHorizontalList)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        internal inner class MyViewHolder(private var itemCheckboxHorizontalList: ItemCheckboxHorizontalBinding) :
            RecyclerView.ViewHolder(itemCheckboxHorizontalList.root) {
            fun bind(cbItem: ModelCheckBox) {
                itemCheckboxHorizontalList.modelCheckBox = cbItem
                itemCheckboxHorizontalList.executePendingBindings()

            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        item = menu.findItem(R.id.button_actionable)
        item?.isVisible = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.button_actionable -> {
                val intent = Intent(mContext, SettingActivity::class.java)
                startActivityForResult(intent,1)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            when (resultCode) {
                RESULT_OK -> {
                    Int
                    viewModel.number.set(data?.getIntExtra("countCheckBox", 50))
                    initComponent()

                }
            }

        }

    }
}