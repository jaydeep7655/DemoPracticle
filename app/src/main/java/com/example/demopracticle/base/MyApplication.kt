package com.example.demopracticle.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex


class MyApplication : Application() {
    private var context: Context? = null
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        context = getApplicationContext();
            }


}