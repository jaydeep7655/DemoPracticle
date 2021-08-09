package com.example.demopracticle.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.example.demopracticle.R
import com.example.demopracticle.base.ViewModelBase
import com.example.demopracticle.model.ModelCheckBox
import com.example.demopracticle.model.ModelMain
import com.example.demopracticle.utlity.SingleLiveEvent
import com.example.demopracticle.utlity.UDF
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ViewModelActivityMain(application: Application) : ViewModelBase(application) {
     private val liveClickEvent = SingleLiveEvent<Int>()
    var number = ObservableField(50)

    var list = ArrayList<ModelMain>()
    fun getLiveClickEvent(): SingleLiveEvent<Int> {
        return liveClickEvent
    }
    fun initData(){
        list = ArrayList<ModelMain>()
        for(i in 1..number.get()!!){
            val innerList = ArrayList<ModelCheckBox>()
            for(j in 0 until i) {
                innerList.add(ModelCheckBox(count = i, value = false))
            }
            list.add(ModelMain(i, innerList))
        }
    }



    fun onClickEvent(viewId: Int) {
        when (viewId) {

            R.id.btnSubmit -> {
                liveClickEvent.value = R.id.btnSubmit
                liveClickEvent.call()
            }
            R.id.btnLog -> {
                liveClickEvent.value = R.id.btnLog
                liveClickEvent.call()
            }
        }
    }

    fun onClickEvent(viewId: Int, position: Int, id: Int) {
        when (viewId) {

        }

    }

    fun onClickEvent(viewId: Int, position: Int) {
        when (viewId) {

        }
    }

}