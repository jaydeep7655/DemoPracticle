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

class ViewModelActivityLog(application: Application) : ViewModelBase(application) {
     private val liveClickEvent = SingleLiveEvent<Int>()

    var list = ArrayList<ModelMain>()
    fun getLiveClickEvent(): SingleLiveEvent<Int> {
        return liveClickEvent
    }




    fun onClickEvent(viewId: Int) {
        when (viewId) {



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